package com.herecity.place.application.port.input

import com.herecity.place.application.dto.GetPlacesDto
import com.herecity.place.application.dto.PlaceDto
import org.springdoc.core.converters.models.Pageable
import org.springframework.data.domain.Page

interface LoadPlaceUseCase {
  fun getPlaces(getPlacesDto: GetPlacesDto, pageable: Pageable): Page<PlaceDto>
}
