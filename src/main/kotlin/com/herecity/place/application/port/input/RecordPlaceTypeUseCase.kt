package com.herecity.place.application.port.input

import com.herecity.place.application.dto.CreatePlaceTypeDto
import com.herecity.place.application.dto.PlaceTypeDto

interface RecordPlaceTypeUseCase {
  fun createPlaceType(createPlaceTypeDto: CreatePlaceTypeDto): PlaceTypeDto
  fun updatePlaceType(id: Long, name: String): PlaceTypeDto
  fun deletePlaceType(id: Long)
}
