package com.herecity.region.adapter.dto

import com.herecity.region.domain.entity.City
import com.herecity.region.domain.entity.Street

data class StreetDto(
  val id: Long?,
  val name: String,
  val city: CityDto
) {
  constructor(id: Long, name: String, cityId: Long, cityName: String) : this(id, name, CityDto(cityId, cityName))
  constructor(street: Street, city: City) : this(id = street.id, name = street.name, city = CityDto(city.id, city.name))
}
