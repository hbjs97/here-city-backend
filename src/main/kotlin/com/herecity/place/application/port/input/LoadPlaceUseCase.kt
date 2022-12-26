package com.herecity.place.application.port.input

import com.herecity.place.application.dto.PlaceDto

interface LoadPlaceUseCase {
  fun getPlaces(): List<PlaceDto>
}
