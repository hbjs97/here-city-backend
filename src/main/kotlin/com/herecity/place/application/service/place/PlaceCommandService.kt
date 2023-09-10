package com.herecity.place.application.service.place

import com.herecity.activity.application.port.outbound.ActivityQueryOutputPort
import com.herecity.place.application.dto.Coordinate2D
import com.herecity.place.application.port.input.place.CreatePlaceCommand
import com.herecity.place.application.port.input.place.DeletePlaceCommand
import com.herecity.place.application.port.input.place.SavePlaceRatingCommand
import com.herecity.place.application.port.input.place.UpdatePlaceCommand
import com.herecity.place.application.port.output.place.PlaceCommandOutputPort
import com.herecity.place.application.port.output.place.PlaceQueryOutputPort
import com.herecity.place.application.port.output.type.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.Place
import com.herecity.place.domain.entity.PlaceActivity
import com.herecity.place.domain.entity.PlaceTypeGroup
import com.herecity.place.domain.entity.PlaceUnit
import com.herecity.unit.application.port.outbound.UnitQueryOutputPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceCommandService(
    private val activityQueryOutputPort: ActivityQueryOutputPort,
    private val unitQueryOutputPort: UnitQueryOutputPort,
    private val placeQueryOutputPort: PlaceQueryOutputPort,
    private val placeCommandOutputPort: PlaceCommandOutputPort,
    private val placeTypeQueryOutputPort: PlaceTypeQueryOutputPort,
) : CreatePlaceCommand, UpdatePlaceCommand, SavePlaceRatingCommand, DeletePlaceCommand {
    @Transactional
    override fun createPlace(command: CreatePlaceCommand.In): CreatePlaceCommand.Out {
        val activities = activityQueryOutputPort.getByIds(command.activityIds)
        val units = unitQueryOutputPort.getByIds(command.unitIds)
        val placeTypes = placeTypeQueryOutputPort.getByIds(command.placeTypeIds)
        val place = Place(
            title = command.title,
            name = command.name,
            address = command.address,
            point = command.point,
            regionId = command.regionId,
            description = command.description,
            images = command.images,
            visitDate = command.visitDate,
            rating = 0.0
        )
        place.placeActivities.addAll(activities.map { v -> PlaceActivity(place = place, activity = v) })
        place.placeUnits.addAll(units.map { v -> PlaceUnit(place = place, unit = v) })
        place.placeTypes.addAll(placeTypes.map { v -> PlaceTypeGroup(place = place, type = v) })
        placeCommandOutputPort.save(place).let {
            return CreatePlaceCommand.Out(
                id = it.id,
                title = it.title,
                name = it.name,
                address = it.address,
                point = Coordinate2D(it.point.x, it.point.y),
                rating = it.rating,
                description = it.description,
                images = it.images,
            )
        }
    }

    @Transactional
    override fun deletePlace(command: DeletePlaceCommand.In) {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun savePlaceRating(command: SavePlaceRatingCommand.In): SavePlaceRatingCommand.Out {
        val place = placeQueryOutputPort.getById(command.id)
        place.rating = command.rating
        placeCommandOutputPort.save(place).let {
            return SavePlaceRatingCommand.Out(
                id = it.id,
                title = it.title,
                name = it.name,
                address = it.address,
                point = Coordinate2D(it.point.x, it.point.y),
                rating = it.rating,
                description = it.description,
                images = it.images,
            )
        }
    }

    @Transactional
    override fun updatePlace(command: UpdatePlaceCommand.In): UpdatePlaceCommand.Out {
        TODO("Not yet implemented")
    }
}
