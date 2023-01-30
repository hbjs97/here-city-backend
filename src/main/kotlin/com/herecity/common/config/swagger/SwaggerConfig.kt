package com.herecity.common.config.swagger

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
  name = "Bearer Authentication",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  scheme = "bearer"
)
class SwaggerConfig {
  @Bean
  fun api(): OpenAPI {
    return OpenAPI().info(
      Info().title("HereCity API Docs").version("0.0.1-SNAPSHOT")
    )
  }
}
