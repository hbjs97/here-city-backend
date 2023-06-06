package com.herecity.user.application.security.oauth2

import com.herecity.common.config.app.AppProperties
import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.application.security.CookieUtils
import com.herecity.user.application.security.JwtService
import com.herecity.user.application.security.oauth2.OAuth2UserInfoFactory.getOAuth2UserInfo
import com.herecity.user.domain.vo.ProviderType
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import java.net.URI
import java.util.Locale
import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationSuccessHandler(
    private val jwtService: JwtService,
    private val appProperties: AppProperties,
    private val authorizationRequestRepository: OAuth2AuthorizationRequestBasedOnCookieRepository,
    private val userQueryOutputPort: UserQueryOutputPort,
) : SimpleUrlAuthenticationSuccessHandler() {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val targetUrl = determineTargetUrl(request, response, authentication)
        if (response.isCommitted) {
            log.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }
        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ): String {
        val redirectUri = CookieUtils.getCookie(
            request,
            OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME
        )
            .map(Cookie::getValue)
        require(!(redirectUri.isPresent && !isAuthorizedRedirectUri(redirectUri.get()))) {
            "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication"
        }

        val targetUrl = redirectUri.orElse(defaultTargetUrl)
        val authToken = authentication as OAuth2AuthenticationToken
        val providerType = ProviderType.valueOf(authToken.authorizedClientRegistrationId.uppercase(Locale.getDefault()))
        val userInfo = getOAuth2UserInfo(providerType.toString(), authentication.principal.attributes)
        val user = userQueryOutputPort.findByEmail(userInfo.getEmail()) ?: throw NoSuchElementException(
            "해당 유저가 존재하지 않습니다."
        )
        val accessToken = jwtService.createAccessToken(
            UserDto(
                id = user.id,
                email = user.email,
                displayName = user.displayName,
                role = user.role
            )
        )

//        // refresh 토큰 설정
//        val refreshTokenExpiry: Long = appProperties.auth.getRefreshTokenExpiry()
//        val refreshToken: AuthToken = tokenProvider.createAuthToken(
//            appProperties.auth.tokenSecret(),
//            Date(now.getTime() + refreshTokenExpiry)
//        )

//        // DB 저장
//        var userRefreshToken: UserRefreshToken? = userRefreshTokenRepository.findByUserId(userInfo.getId())
//        if (userRefreshToken != null) {
//            userRefreshToken.setRefreshToken(refreshToken.getToken())
//        } else {
//            userRefreshToken = UserRefreshToken(userInfo.getId(), refreshToken.getToken())
//            userRefreshTokenRepository.saveAndFlush(userRefreshToken)
//        }

        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("token", accessToken)
            .build().toUriString()
    }

    private fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    private fun hasAuthority(authorities: Collection<GrantedAuthority> = emptyList(), authority: String): Boolean {
        for (grantedAuthority in authorities) {
            if (authority == grantedAuthority.authority) {
                return true
            }
        }
        return false
    }

    private fun isAuthorizedRedirectUri(uri: String): Boolean {
        val clientRedirectUri = URI.create(uri)
        return appProperties.oauth2.authorizedRedirectUris
            .stream()
            .anyMatch {
                // Only validate host and port. Let the clients use different paths if they want to
                val authorizedURI: URI = URI.create(it)
                if (authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true) &&
                    authorizedURI.port === clientRedirectUri.port
                ) {
                    return@anyMatch true
                }
                false
            }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
