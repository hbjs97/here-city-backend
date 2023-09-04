package com.herecity.tour.application.service

import com.herecity.place.application.dto.Coordinate2D
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.place.FetchPlaceQuery
import com.herecity.place.application.port.input.place.FetchPlacesQuery
import com.herecity.tour.application.port.input.CreateTourCommand
import com.herecity.tour.application.port.input.FetchTourPlanQuery
import com.herecity.tour.application.port.input.UpdateTourCommand
import com.herecity.tour.application.port.input.UpdateTourPlaceCommand
import com.herecity.tour.application.port.output.TourOutputPort
import com.herecity.tour.domain.entity.Tour
import com.herecity.user.application.port.input.FetchUserUseCase
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class TourCommandService(
    private val tourOutputPort: TourOutputPort,
    private val fetchUserUseCase: FetchUserUseCase,
    private val fetchPlaceQuery: FetchPlaceQuery,
    private val fetchPlacesQuery: FetchPlacesQuery,
    private val fetchTourPlanQuery: FetchTourPlanQuery,
) : CreateTourCommand, UpdateTourCommand, UpdateTourPlaceCommand {
    override fun createTour(command: CreateTourCommand.In): CreateTourCommand.Out {
        val users = fetchUserUseCase.fetchUsers(command.tourists.toList())
        if (users.size != command.tourists.size) {
            throw NotFoundException("Invalid tourists")
        }

        val places = fetchPlacesQuery.fetchPlaces(
            FetchPlacesQuery.In(
                ids = command.tourPlaces.map { it.placeId }
            )
        ).places
        if (places.size != command.tourPlaces.size) {
            throw NotFoundException("Invalid places")
        }

        val tour = Tour(
            name = command.name,
            regionId = command.regionId,
            createdBy = command.createdBy,
            scope = command.scope,
            from = command.from,
            to = command.to,
        )
        command.tourPlaces.forEach {
            tour.addTourPlace(it)
        }
        command.tourists.forEach {
            tour.addTourist(it)
        }
        tourOutputPort.save(tour)

        fetchTourPlanQuery.fetchTourPlan(FetchTourPlanQuery.In(id = tour.id)).let {
            return CreateTourCommand.Out(
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

    override fun updateTour(command: UpdateTourCommand.In): UpdateTourCommand.Out {
        val tour = tourOutputPort.getById(command.id)
        command.name?.let { tour.name = it }
        command.scope?.let { tour.scope = it }
        command.from?.let { tour.from = it }
        command.to?.let { tour.to = it }
        tourOutputPort.save(tour)

        fetchTourPlanQuery.fetchTourPlan(FetchTourPlanQuery.In(id = tour.id)).let {
            return UpdateTourCommand.Out(
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

    override fun updateTourPlace(command: UpdateTourPlaceCommand.In): UpdateTourPlaceCommand.Out {
        val tour = tourOutputPort.getById(command.id)
        val tourPlace = tour.tourPlaces.firstOrNull { it.placeId == command.placeId }
            ?: throw NoSuchElementException("${command.placeId} is not in tour ${command.id}")
        command.from?.let { tourPlace.from = it }
        command.to?.let { tourPlace.to = it }
        command.budgets.isNotEmpty().let { tourPlace.budgets = command.budgets }
        tourOutputPort.save(tour)
        val place = fetchPlaceQuery.fetchPlace(FetchPlaceQuery.In(id = command.placeId))
        return UpdateTourPlaceCommand.Out(
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
