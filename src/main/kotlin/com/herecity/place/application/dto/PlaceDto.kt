package com.herecity.place.application.dto

import javax.validation.constraints.Size


data class PlaceDto(
  val id: Long?,

  @Size(min = 2, max = 100)
  val name: String,
) {

}
