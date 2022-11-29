package com.herecity.region.application.port.input

import com.herecity.region.adapter.dto.CityDto
import com.herecity.region.adapter.dto.StreetDto
import com.herecity.region.application.dto.UpdateStreetDto

interface RecordRegionUseCase {
  fun createCity(name: String): CityDto
  fun updateCityName(id: Long, name: String): CityDto
  fun deleteCity(id: Long)

  fun addStreet(cityId: Long, name: String): StreetDto
  fun updateStreet(id: Long, updateStreetDto: UpdateStreetDto): StreetDto
  fun deleteStreet(id: Long)
}
