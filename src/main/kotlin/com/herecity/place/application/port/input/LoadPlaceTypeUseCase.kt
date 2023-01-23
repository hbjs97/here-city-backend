package com.herecity.place.application.port.input

import com.herecity.place.application.dto.PlaceTypeDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface LoadPlaceTypeUseCase {
  fun getPlaceTypes(pageable: Pageable): Page<PlaceTypeDto>
}
