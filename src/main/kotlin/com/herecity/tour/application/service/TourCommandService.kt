package com.herecity.tour.application.service

import com.herecity.place.application.dto.Coordinate2D
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.FetchPlaceQuery
import com.herecity.place.application.port.input.FetchPlacesQuery
import com.herecity.tour.application.dto.CreateTourDto
import com.herecity.tour.application.dto.TourPlaceDto
import com.herecity.tour.application.dto.TourPlanDto
import com.herecity.tour.application.dto.UpdateTourDto
import com.herecity.tour.application.dto.UpdateTourPlaceDto
import com.herecity.tour.application.port.input.FetchTourPlanQuery
import com.herecity.tour.application.port.input.SaveTourUseCase
import com.herecity.tour.application.port.output.TourOutputPort
import com.herecity.tour.domain.entity.Tour
import com.herecity.user.application.port.input.FetchUserUseCase
import org.springframework.stereotype.Service
import org.webjars.NotFoundException
import java.util.UUID

@Service
class TourCommandService(
    private val tourOutputPort: TourOutputPort,
    private val fetchUserUseCase: FetchUserUseCase,
    private val fetchPlaceQuery: FetchPlaceQuery,
    private val fetchPlacesQuery: FetchPlacesQuery,
    private val fetchTourPlanQuery: FetchTourPlanQuery,
) : SaveTourUseCase {
    override fun createTour(createTourDto: CreateTourDto, createdBy: UUID): TourPlanDto {
        val users = fetchUserUseCase.fetchUsers(createTourDto.tourists)
        if (users.size != createTourDto.tourists.size) throw NotFoundException("Invalid tourists")

        val places = fetchPlacesQuery.fetchPlaces(
            FetchPlacesQuery.In(
                ids = createTourDto.tourPlaces.map { it.placeId }
            )
        ).places
        if (places.size != createTourDto.tourPlaces.size) throw NotFoundException("Invalid places")

        val tour = tourOutputPort.save(Tour(createTourDto, createdBy))
        fetchTourPlanQuery.fetchTourPlan(FetchTourPlanQuery.In(id = tour.id)).let {
            return TourPlanDto(
                id = it.id,
                ownerName = it.ownerName,
                tourName = it.tourName,
                regionName = it.regionName,
                scope = it.scope,
                from = it.from,
                to = it.to,
                notifications = it.notifications,
                tourPlaces = it.tourPlaces
            )
        }
    }

    override fun updateTour(id: Long, updateTourDto: UpdateTourDto): TourPlanDto {
        val tour = tourOutputPort.getById(id)
        updateTourDto.name?.let { tour.name = it }
        updateTourDto.scope?.let { tour.scope = it }
        updateTourDto.from?.let { tour.from = it }
        updateTourDto.to?.let { tour.to = it }
        tourOutputPort.save(tour)

        fetchTourPlanQuery.fetchTourPlan(FetchTourPlanQuery.In(id = tour.id)).let {
            return TourPlanDto(
                id = it.id,
                ownerName = it.ownerName,
                tourName = it.tourName,
                regionName = it.regionName,
                scope = it.scope,
                from = it.from,
                to = it.to,
                notifications = it.notifications,
                tourPlaces = it.tourPlaces
            )
        }
    }

    override fun updateTourPlace(id: Long, placeId: Long, updateTourPlaceDto: UpdateTourPlaceDto): TourPlaceDto {
        val tour = tourOutputPort.getById(id)
        val tourPlace = tour.tourPlaces.firstOrNull { it.placeId == placeId }
            ?: throw NoSuchElementException("$placeId is not in tour $id")
        updateTourPlaceDto.from?.let { tourPlace.from = it }
        updateTourPlaceDto.to?.let { tourPlace.to = it }
        updateTourPlaceDto.budgets.isNotEmpty().let { tourPlace.budgets = updateTourPlaceDto.budgets }
        tourOutputPort.save(tour)
        val place = fetchPlaceQuery.fetchPlace(FetchPlaceQuery.In(id = placeId))
        return TourPlaceDto(
            place = PlaceDto(
                id = place.id,
                title = place.title,
                name = place.name,
                description = place.description,
                address = place.address,
                point = Coordinate2D(place.point),
                rating = place.rating,
                images = place.images,
            ),
            from = tourPlace.from,
            to = tourPlace.to,
            budgets = tourPlace.budgets,
        )
    }
}
