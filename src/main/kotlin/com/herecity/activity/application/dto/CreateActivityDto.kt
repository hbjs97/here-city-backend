package com.herecity.activity.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.annotations.NotNull

data class CreateActivityDto(
  @Schema(maxLength = 20, required = true)
  @NotNull
  val name: String
)
