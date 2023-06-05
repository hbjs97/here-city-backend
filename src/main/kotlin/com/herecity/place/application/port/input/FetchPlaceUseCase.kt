package com.herecity.place.application.port.input

import com.herecity.place.application.dto.GetPlacesDto
import com.herecity.place.application.dto.PlaceDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface FetchPlaceUseCase {
    fun getPlaces(userId: UUID, getPlacesDto: GetPlacesDto, pageable: Pageable): Page<PlaceDto>
    fun fetchPlace(id: Long): PlaceDto
    fun fetchPlaces(ids: List<Long>): List<PlaceDto>
}
