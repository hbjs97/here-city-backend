package com.herecity.member.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema

data class SearchMemberDto(
  @Schema(required = false)
  val unitId: Long?
)
