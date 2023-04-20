package com.herecity.place.application.service

import com.herecity.activity.application.port.output.ActivityQueryOutputPort
import com.herecity.place.application.dto.CreatePlaceDto
import com.herecity.place.application.dto.GetPlacesDto
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.LoadPlaceUseCase
import com.herecity.place.application.port.input.RecordPlaceUseCase
import com.herecity.place.application.port.output.PlaceCommandOutputPort
import com.herecity.place.application.port.output.PlaceQueryOutputPort
import com.herecity.place.application.port.output.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.Place
import com.herecity.place.domain.entity.PlaceActivity
import com.herecity.place.domain.entity.PlaceTypeGroup
import com.herecity.place.domain.entity.PlaceUnit
import com.herecity.place.domain.service.DistanceCalculator
import com.herecity.place.domain.vo.PositionVO
import com.herecity.unit.application.port.output.UnitQueryOutputPort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PlaceService(
  private val activityQueryOutputPort: ActivityQueryOutputPort,
  private val unitQueryOutputPort: UnitQueryOutputPort,
  private val placeQueryOutputPort: PlaceQueryOutputPort,
  private val placeCommandOutputPort: PlaceCommandOutputPort,
  private val placeTypeQueryOutputPort: PlaceTypeQueryOutputPort,
  private val calculator: DistanceCalculator
) : LoadPlaceUseCase, RecordPlaceUseCase {

  override fun getPlaces(getPlacesDto: GetPlacesDto, pageable: Pageable): Page<PlaceDto> {
    val places = this.placeQueryOutputPort.search(getPlacesDto, pageable)
    if (getPlacesDto.point != null) {
      places.content.forEach { v ->
        val inputPoint = PositionVO(getPlacesDto.point)
        v.distance = calculator.measure(inputPoint, PositionVO(v.point))
      }
    }
    return places
  }

  @Transactional
  override fun createPlace(createPlaceDto: CreatePlaceDto): PlaceDto {
    val activities = this.activityQueryOutputPort.getByIds(createPlaceDto.activityIds)
    val units = this.unitQueryOutputPort.getByIds(createPlaceDto.unitIds)
    val placeTypes = this.placeTypeQueryOutputPort.getByIds(createPlaceDto.placeTypeIds)
    val place = Place(
      title = createPlaceDto.title,
      name = createPlaceDto.name,
      address = createPlaceDto.address,
      point = createPlaceDto.point,
      regionId = createPlaceDto.regionId,
      description = createPlaceDto.description,
      images = createPlaceDto.images,
      visitDate = createPlaceDto.visitDate,
      rating = 0.0
    )
    place.placeActivities.addAll(activities.map { v -> PlaceActivity(place = place, activity = v) })
    place.placeUnits.addAll(units.map { v -> PlaceUnit(place = place, unit = v) })
    place.placeTypes.addAll(placeTypes.map { v -> PlaceTypeGroup(place = place, type = v) })
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
