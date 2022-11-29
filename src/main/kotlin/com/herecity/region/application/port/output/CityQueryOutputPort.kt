package com.herecity.region.application.port.output

import com.herecity.region.adapter.dto.CityDto
import com.herecity.region.domain.entity.City


interface CityQueryOutputPort {
  fun getAllCities(): List<CityDto>
  fun getCityById(id: Long): City
  fun findCityById(id: Long): City?
  fun findCityByName(name: String): City?
  fun existsCityByName(name: String): Boolean
}
