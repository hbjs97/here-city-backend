package com.herecity.place.application.dto

import com.herecity.place.domain.entity.Place
import javax.validation.constraints.Size

data class PlaceDto(
  val id: Long?,

  @Size(min = 2, max = 100)
  val name: String,
) {
  constructor(place: Place) : this(id = place.id, name = place.name)
}
