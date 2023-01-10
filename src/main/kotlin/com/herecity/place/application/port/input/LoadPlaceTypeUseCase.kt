package com.herecity.place.application.port.input

import com.herecity.place.application.dto.PlaceTypeDto
import org.springdoc.core.converters.models.Pageable
import org.springframework.data.domain.Page

interface LoadPlaceTypeUseCase {
  fun getPlaceTypes(pageable: Pageable): Page<PlaceTypeDto>
}
