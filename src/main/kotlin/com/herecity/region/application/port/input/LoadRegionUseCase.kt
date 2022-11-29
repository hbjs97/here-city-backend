package com.herecity.region.application.port.input

import com.herecity.region.adapter.dto.CityDto
import com.herecity.region.adapter.dto.StreetDto

interface LoadRegionUseCase {
  fun getCities(): List<CityDto>
  fun getStreets(cityId: Long?): List<StreetDto>
}
