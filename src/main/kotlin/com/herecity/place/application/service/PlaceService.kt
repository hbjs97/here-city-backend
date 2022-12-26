package com.herecity.place.application.service

import com.herecity.place.application.dto.CreatePlaceDto
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.LoadPlaceUseCase
import com.herecity.place.application.port.input.RecordPlaceUseCase
import com.herecity.place.application.port.output.PlaceCommandOutputPort
import com.herecity.place.application.port.output.PlaceQueryOutputPort
import org.springframework.stereotype.Service

@Service
class PlaceService(
  private val placeQueryOutputPort: PlaceQueryOutputPort,
  private val placeCommandOutputPort: PlaceCommandOutputPort,
) : LoadPlaceUseCase, RecordPlaceUseCase {
  override fun getPlaces(): List<PlaceDto> {
    TODO("Not yet implemented")
  }

  override fun createPlace(createPlaceDto: CreatePlaceDto): PlaceDto {
    TODO("Not yet implemented")
  }

  override fun updatePlace(id: Long, updatePlaceDto: CreatePlaceDto): PlaceDto {
    TODO("Not yet implemented")
  }

  override fun deletePlace(id: Long) {
    TODO("Not yet implemented")
  }

}
