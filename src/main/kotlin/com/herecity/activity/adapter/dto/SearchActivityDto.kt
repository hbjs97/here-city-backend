package com.herecity.activity.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema

data class SearchActivityDto(
  @Schema(required = false)
  val name: String?
)
