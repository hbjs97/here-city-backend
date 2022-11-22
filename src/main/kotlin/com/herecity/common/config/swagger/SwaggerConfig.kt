package com.herecity.common.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
  @Bean
  fun api(): OpenAPI {
    val info = Info().title("HereCity API Docs").version("0.0.1-SNAPSHOT")
    val securityScheme = SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT").`in`(SecurityScheme.In.HEADER).name("Authorization")
    val schemaRequirement = SecurityRequirement().addList("BearerToken")
    return OpenAPI()
      .components(Components().addSecuritySchemes("BearerToken", securityScheme))
      .security(listOf(schemaRequirement))
      .info(info)
  }
}
