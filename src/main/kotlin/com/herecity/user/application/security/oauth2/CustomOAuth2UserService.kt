package com.herecity.user.application.security.oauth2

import com.herecity.user.application.port.outbound.UserCommandOutputPort
import com.herecity.user.application.port.outbound.UserQueryOutputPort
import com.herecity.user.domain.entity.User
import com.herecity.user.domain.exception.OAuth2AuthenticationProcessingException
import com.herecity.user.domain.vo.ProviderType
import com.herecity.user.domain.vo.UserDetail
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.UUID
import javax.naming.AuthenticationException

@Service
class CustomOAuth2UserService(
    private val userQueryOutputPort: UserQueryOutputPort,
    private val userCommandOutputPort: UserCommandOutputPort,
) : DefaultOAuth2UserService() {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(oAuth2UserRequest)
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo: OAuth2UserInfo =
            OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.clientRegistration.registrationId,
                oAuth2User.attributes
            )
        if (oAuth2UserInfo.getId().isEmpty()) {
            throw OAuth2AuthenticationProcessingException("ProviderId not found from OAuth2 provider")
        }
        val user = userQueryOutputPort.findByProviderId(oAuth2UserInfo.getId())
        return if (user != null) {
            if (
                user.provider != ProviderType.valueOf(oAuth2UserRequest.clientRegistration.registrationId.uppercase())
            ) {
                throw OAuth2AuthenticationProcessingException(
                    "Looks like you're signed up with ${user.provider} account." +
                        "Please use your ${user.provider} account to login."
                )
            }
            UserDetail(user).apply {
                this.attributes = oAuth2User.attributes
            }
        } else {
            UserDetail(registerNewUser(oAuth2UserRequest, oAuth2UserInfo)).apply {
                this.attributes = oAuth2User.attributes
            }
        }
    }

    private fun registerNewUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo): User {
        return userCommandOutputPort.save(
            User(
                id = UUID.randomUUID(),
                providerId = oAuth2UserInfo.getId(),
                provider = ProviderType.valueOf(oAuth2UserRequest.clientRegistration.registrationId.uppercase()),
                displayName = oAuth2UserInfo.getName(),
                email = oAuth2UserInfo.getEmail(),
                thumbnail = oAuth2UserInfo.getImageUrl()
            )
        )
    }

//    private fun updateExistingUser(existingUser: User, oAuth2UserInfo: OAuth2UserInfo): User {
//        existingUser.providerId = oAuth2UserInfo.getName()
//        existingUser.thumbnail = oAuth2UserInfo.getImageUrl()
//        return userCommandOutputPort.save(existingUser)
//    }
}
