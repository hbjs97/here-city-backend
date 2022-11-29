package com.herecity.region.adapter.dto

import com.herecity.region.domain.entity.City

data class CityDto(
  val id: Long?,
  val name: String
) {
  constructor(city: City) : this(city.id, city.name)
}
