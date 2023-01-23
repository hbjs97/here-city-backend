package com.herecity.region.adapter.dto

import javax.validation.constraints.Size

data class NameDto(
  val name: @Size(min = 1, max = 20) String
)
