package com.herecity.common.config.app

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app")
class AppProperties {
    val auth = Auth()
    val oauth2 = OAuth2()

    class Auth {
        var accessTokenSecret: String = ""
        var accessTokenTtlMsec: Long = 0L
    }

    class OAuth2 {
        var googleAndroidClientId: String = ""
        var googleIOSClientId: String = ""
        var authorizedRedirectUris: List<String> = ArrayList()
            private set

        fun googleAndroidClientId(googleAndroidClientId: String): OAuth2 {
            this.googleAndroidClientId = googleAndroidClientId
            return this
        }

        fun googleIOSClientId(googleIOSClientId: String): OAuth2 {
            this.googleIOSClientId = googleIOSClientId
            return this
        }

        fun authorizedRedirectUris(authorizedRedirectUris: List<String>): OAuth2 {
            this.authorizedRedirectUris = authorizedRedirectUris
            return this
        }
    }
}
