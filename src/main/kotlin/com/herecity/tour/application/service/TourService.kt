package com.herecity.tour.application.service

import com.herecity.place.application.port.input.FetchPlaceUseCase
import com.herecity.region.application.port.input.FetchRegionUseCase
import com.herecity.tour.application.dto.CreateTourDto
import com.herecity.tour.application.dto.TourNotificationDto
import com.herecity.tour.application.dto.TourPlaceDto
import com.herecity.tour.application.dto.TourPlanDto
import com.herecity.tour.application.port.input.FetchTourUseCase
import com.herecity.tour.application.port.input.SaveTourUseCase
import com.herecity.tour.application.port.input.ShareTourUseCase
import com.herecity.tour.application.port.output.TourOutputPort
import com.herecity.tour.domain.entity.Tour
import com.herecity.user.application.port.input.FetchUserUseCase
import org.springframework.stereotype.Service
import org.webjars.NotFoundException
import java.util.*

@Service
class TourService(
  private val tourOutputPort: TourOutputPort,
  private val fetchRegionUseCase: FetchRegionUseCase,
  private val fetchUserUseCase: FetchUserUseCase,
  private val fetchPlaceUseCase: FetchPlaceUseCase
) : SaveTourUseCase, ShareTourUseCase, FetchTourUseCase {
  override fun createTour(createTourDto: CreateTourDto, createdBy: UUID) {
    val users = fetchUserUseCase.fetchUsers(createTourDto.tourists)
    if (users.size != createTourDto.tourists.size) throw NotFoundException("Invalid tourists")

    val places = fetchPlaceUseCase.fetchPlaces(createTourDto.tourPlaces.map { it.placeId })
    if (places.size != createTourDto.tourPlaces.size) throw NotFoundException("Invalid places")

    tourOutputPort.save(Tour(createTourDto, createdBy))
  }

  override fun shareJoinCode(id: Long): String = tourOutputPort.getById(id).joinCode.toString()

  override fun fetchTourPlan(id: Long): TourPlanDto {
    val tour = tourOutputPort.getById(id)
    val region = fetchRegionUseCase.getById(tour.regionId)
    val tourOwner = fetchUserUseCase.fetchUser(tour.createdBy)

    val places = fetchPlaceUseCase.fetchPlaces(tour.tourPlaces.map { it.placeId }).toSet()
    if (places.size != tour.tourPlaces.size) throw NotFoundException("Invalid places")

    val tourPlaces = tour.tourPlaces.map { TourPlaceDto(it, places.first { place -> place.id == it.placeId }) }
    return TourPlanDto(
      ownerName = tourOwner.displayName,
      tourName = tour.name,
      regionName = region.name,
      scope = tour.scope.korName,
      from = tour.from,
      to = tour.to,
      notifications = tour.tourNotifications.map { TourNotificationDto(it) },
      tourPlaces = tourPlaces
    )
  }
}