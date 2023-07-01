package com.herecity.common.config.app

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app")
class AppProperties {
    val auth = Auth()
    val oauth2 = OAuth2()
    val aws = Aws()

    class Auth {
        var accessTokenSecret: String = ""
        var accessTokenTtlMsec: Long = 0L
    }

    class OAuth2 {
        var authorizedRedirectUris: List<String> = ArrayList()
            private set

        fun authorizedRedirectUris(authorizedRedirectUris: List<String>): OAuth2 {
            this.authorizedRedirectUris = authorizedRedirectUris
            return this
        }
    }

    class Aws {
        var accessKeyId: String = ""
        var secretAccessKey: String = ""
        var s3 = S3()

        class S3 {
            var domain: String = ""
            var bucketName: String = ""
            var region: String = ""
        }
    }
}
