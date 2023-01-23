package com.herecity.unit.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.annotations.NotNull

data class CreateUnitDto(
  @Schema(maxLength = 20, required = true)
  @NotNull
  val name: String
)
