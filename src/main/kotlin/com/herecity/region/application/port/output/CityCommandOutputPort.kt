package com.herecity.region.application.port.output

import com.herecity.region.domain.entity.City

interface CityCommandOutputPort {
  fun saveCity(entity: City): City
  fun deleteCityById(id: Long)
}
