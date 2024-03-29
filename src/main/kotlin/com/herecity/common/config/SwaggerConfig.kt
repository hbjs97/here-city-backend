package com.herecity.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    @Value("\${springdoc.swagger-ui.server-uri}") private val serverUri: String,
) {
    @Bean
    fun api(): OpenAPI {
        val info = Info().title("HereCity API Docs").version("0.0.1-SNAPSHOT")
        val securityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name("Authorization")
        val openApi = OpenAPI()
            .components(Components().addSecuritySchemes("BearerToken", securityScheme))
            .info(info)
        if (serverUri.isNotEmpty()) openApi.servers(listOf(Server().url(serverUri)))
        return openApi
    }
}
