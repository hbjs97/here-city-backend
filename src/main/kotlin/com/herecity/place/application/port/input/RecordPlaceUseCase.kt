package com.herecity.place.application.port.input

import com.herecity.place.application.dto.CreatePlaceDto
import com.herecity.place.application.dto.PlaceDto

interface RecordPlaceUseCase {
    fun createPlace(createPlaceDto: CreatePlaceDto): PlaceDto
    fun updatePlace(id: Long, updatePlaceDto: CreatePlaceDto): PlaceDto
    fun savePlaceRating(id: Long, rating: Double): PlaceDto
    fun deletePlace(id: Long)
}
