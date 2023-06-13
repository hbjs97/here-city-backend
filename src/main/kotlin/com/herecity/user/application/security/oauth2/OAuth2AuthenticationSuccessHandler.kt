package com.herecity.user.application.security.oauth2

class OAuth2AuthenticationSuccessHandler
// @Component
//    (
//    private val jwtService: JwtService,
//    private val appProperties: AppProperties,
//    private val authorizationRequestRepository: OAuth2AuthorizationRequestBasedOnCookieRepository,
//    private val userQueryOutputPort: UserQueryOutputPort,
// ) : SimpleUrlAuthenticationSuccessHandler() {
//    @Throws(IOException::class, ServletException::class)
//    override fun onAuthenticationSuccess(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        authentication: Authentication,
//    ) {
//        val targetUrl = determineTargetUrl(request, response, authentication)
//        if (response.isCommitted) {
//            log.debug("Response has already been committed. Unable to redirect to $targetUrl")
//            return
//        }
//        clearAuthenticationAttributes(request, response)
//        redirectStrategy.sendRedirect(request, response, targetUrl)
//    }
//
//    override fun determineTargetUrl(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        authentication: Authentication,
//    ): String {
//        val redirectUri = CookieUtils.getCookie(
//            request,
//            OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME
//        )
//            .map(Cookie::getValue)
//        require(!(redirectUri.isPresent && !isAuthorizedRedirectUri(redirectUri.get()))) {
//            "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication"
//        }
//
//        val targetUrl = redirectUri.orElse(defaultTargetUrl)
//        val authToken = authentication as OAuth2AuthenticationToken
//        val providerType = ProviderType.valueOf(authToken.authorizedClientRegistrationId.uppercase(Locale.getDefault()))
//        val userInfo = getOAuth2UserInfo(providerType.toString(), authentication.principal.attributes)
//        val user = userQueryOutputPort.findByProviderId(userInfo.getId()) ?: throw NoSuchElementException(
//            "해당 유저가 존재하지 않습니다."
//        )
//        val accessToken = jwtService.createAccessToken(
//            UserDto(
//                id = user.id,
//                email = user.email,
//                displayName = user.displayName,
//                role = user.role,
//                thumbnail = user.thumbnail,
//            )
//        )
//
// //        // refresh 토큰 설정
// //        val refreshTokenExpiry: Long = appProperties.auth.getRefreshTokenExpiry()
// //        val refreshToken: AuthToken = tokenProvider.createAuthToken(
// //            appProperties.auth.tokenSecret(),
// //            Date(now.getTime() + refreshTokenExpiry)
// //        )
//
// //        // DB 저장
// //        var userRefreshToken: UserRefreshToken? = userRefreshTokenRepository.findByUserId(userInfo.getId())
// //        if (userRefreshToken != null) {
// //            userRefreshToken.setRefreshToken(refreshToken.getToken())
// //        } else {
// //            userRefreshToken = UserRefreshToken(userInfo.getId(), refreshToken.getToken())
// //            userRefreshTokenRepository.saveAndFlush(userRefreshToken)
// //        }
//
//        return UriComponentsBuilder.fromUriString(targetUrl)
//            .queryParam("token", accessToken)
//            .build().toUriString()
//    }
//
//    private fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
//        super.clearAuthenticationAttributes(request)
//        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
//    }
//
//    private fun isAuthorizedRedirectUri(uri: String): Boolean {
//        val clientRedirectUri = URI.create(uri)
//        log.info("[Oauth2][CheckAuthorizedRedirectUri] uri: {}", clientRedirectUri)
//        return appProperties.oauth2.authorizedRedirectUris
//            .stream()
//            .anyMatch {
//                // Only validate host and port. Let the clients use different paths if they want to
//                val authorizedURI: URI = URI.create(it)
//                if (authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true) &&
//                    authorizedURI.port === clientRedirectUri.port
//                ) {
//                    return@anyMatch true
//                }
//                log.error("[Oauth2][CheckAuthorizedRedirectUri] uri: {} is not authorized", clientRedirectUri)
//                false
//            }
//    }
//
//    companion object {
//        private val log = LoggerFactory.getLogger(this::class.java)
//    }
// }
