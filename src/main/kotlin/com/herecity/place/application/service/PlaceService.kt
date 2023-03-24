package com.herecity.place.application.service

import com.herecity.activity.application.port.output.ActivityQueryOutputPort
import com.herecity.place.application.dto.CreatePlaceDto
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.LoadPlaceUseCase
import com.herecity.place.application.port.input.RecordPlaceUseCase
import com.herecity.place.application.port.output.PlaceCommandOutputPort
import com.herecity.place.application.port.output.PlaceQueryOutputPort
import com.herecity.place.application.port.output.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.Place
import com.herecity.place.domain.entity.PlaceActivity
import com.herecity.place.domain.entity.PlaceUnit
import com.herecity.unit.application.port.output.UnitQueryOutputPort
import org.springdoc.core.converters.models.Pageable
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PlaceService(
  private val activityQueryOutputPort: ActivityQueryOutputPort,
  private val unitQueryOutputPort: UnitQueryOutputPort,
  private val placeQueryOutputPort: PlaceQueryOutputPort,
  private val placeCommandOutputPort: PlaceCommandOutputPort,
  private val placeTypeQueryOutputPort: PlaceTypeQueryOutputPort,
) : LoadPlaceUseCase, RecordPlaceUseCase {

  override fun getPlaces(pageable: Pageable): Page<PlaceDto> {
    TODO("Not yet implemented")
  }

  @Transactional
  override fun createPlace(createPlaceDto: CreatePlaceDto): PlaceDto {
    val acitivities = this.activityQueryOutputPort.getByIds(createPlaceDto.activityIds)
    val units = this.unitQueryOutputPort.getByIds(createPlaceDto.unitIds)
    val placeType = this.placeTypeQueryOutputPort.getById(createPlaceDto.placeTypeId)
    val place = Place(
      name = createPlaceDto.name,
      placeTypeId = placeType.id!!,
      placeType = placeType,
      address = createPlaceDto.address,
      point = createPlaceDto.point,
      regionId = createPlaceDto.regionId,
    )
    place.placeActivities.addAll(acitivities.map { v -> PlaceActivity(place = place, activity = v) })
    place.placeUnits.addAll(units.map { v -> PlaceUnit(place = place, unit = v) })
    this.placeCommandOutputPort.save(place)
    return PlaceDto(place)
  }

  override fun updatePlace(id: Long, updatePlaceDto: CreatePlaceDto): PlaceDto {
    TODO("Not yet implemented")
  }

  override fun deletePlace(id: Long) {
    TODO("Not yet implemented")
  }
}
