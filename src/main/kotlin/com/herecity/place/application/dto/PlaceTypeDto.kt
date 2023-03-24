package com.herecity.place.application.dto

import com.herecity.place.domain.entity.PlaceType
import javax.validation.constraints.Size

data class PlaceTypeDto(
  val id: Long?,

  @Size(min = 2, max = 50)
  val name: String,
) {
  constructor(placeType: PlaceType) : this(id = placeType.id, name = placeType.name)
}
