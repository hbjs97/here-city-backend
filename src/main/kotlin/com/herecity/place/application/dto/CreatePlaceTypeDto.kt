package com.herecity.place.application.dto

import javax.validation.constraints.Size

data class CreatePlaceTypeDto(
  val name: @Size(min = 2, max = 50) String,
)
