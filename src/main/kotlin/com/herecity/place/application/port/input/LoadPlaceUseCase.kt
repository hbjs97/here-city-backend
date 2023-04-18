package com.herecity.place.application.port.input

import com.herecity.place.application.dto.GetPlacesDto
import com.herecity.place.application.dto.PlaceDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface LoadPlaceUseCase {
  fun getPlaces(getPlacesDto: GetPlacesDto, pageable: Pageable): Page<PlaceDto>
}
