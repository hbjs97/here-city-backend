package com.herecity.place.application.service

import com.herecity.activity.application.port.output.ActivityQueryOutputPort
import com.herecity.place.application.dto.Coordinate2D
import com.herecity.place.application.dto.CreatePlaceDto
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.FetchPlaceLikeQuery
import com.herecity.place.application.port.input.FetchPlaceQuery
import com.herecity.place.application.port.input.FetchPlacesPageQuery
import com.herecity.place.application.port.input.FetchPlacesQuery
import com.herecity.place.application.port.input.RecordPlaceUseCase
import com.herecity.place.application.port.output.PlaceCommandOutputPort
import com.herecity.place.application.port.output.PlaceQueryOutputPort
import com.herecity.place.application.port.output.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.Place
import com.herecity.place.domain.entity.PlaceActivity
import com.herecity.place.domain.entity.PlaceTypeGroup
import com.herecity.place.domain.entity.PlaceUnit
import com.herecity.place.domain.service.DistanceCalculator
import com.herecity.unit.application.port.output.UnitQueryOutputPort
import org.locationtech.jts.geom.Coordinate
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PlaceService(
    private val activityQueryOutputPort: ActivityQueryOutputPort,
    private val unitQueryOutputPort: UnitQueryOutputPort,
    private val placeQueryOutputPort: PlaceQueryOutputPort,
    private val placeCommandOutputPort: PlaceCommandOutputPort,
    private val placeTypeQueryOutputPort: PlaceTypeQueryOutputPort,
    private val fetchPlaceLikeQuery: FetchPlaceLikeQuery,
    private val calculator: DistanceCalculator,
) : FetchPlacesPageQuery, FetchPlacesQuery, FetchPlaceQuery, RecordPlaceUseCase {
    override fun fetchPlacesPage(query: FetchPlacesPageQuery.In): FetchPlacesPageQuery.Out {
        val places = this.placeQueryOutputPort.search(
            regionId = query.regionId,
            activityId = query.activityId,
            unitId = query.unitId,
            offSetPageable = query.offsetPageable,
            placeTypeId = query.placeTypeId,
            name = query.name,
            point = query.point,
        )
        if (query.point != null) {
            places.content.forEach { v ->
                val inputPoint = Coordinate2D(query.point)
                v.distance = calculator.measure(inputPoint, Coordinate2D(v.point.x, v.point.y))
            }
        }
        val placeLikes = this.fetchPlaceLikeQuery.fetchPlaceLike(
            FetchPlaceLikeQuery.In(
                userId = query.userId,
                placeId = places.content.map { v -> v.id }
            )
        )
        placeLikes.forEach { v ->
            places.content.find { p -> p.id == v.placeId }?.liked = v.liked
        }
        return FetchPlacesPageQuery.Out(
            places = places.content,
            meta = places.meta
        )
    }

    override fun fetchPlace(query: FetchPlaceQuery.In): FetchPlaceQuery.Out =
        this.placeQueryOutputPort.getById(query.id).let {
            FetchPlaceQuery.Out(
                id = it.id,
                title = it.title,
                name = it.name,
                description = it.description,
                address = it.address,
                point = Coordinate(it.point.x, it.point.y),
                rating = it.rating,
                images = it.images,
                distance = null
            )
        }

    override fun fetchPlaces(query: FetchPlacesQuery.In): FetchPlacesQuery.Out {
        val places = this.placeQueryOutputPort.findAllById(query.ids)
        return FetchPlacesQuery.Out(places = places.map { v -> PlaceDto(v) })
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

    override fun savePlaceRating(id: Long, rating: Double): PlaceDto {
        val place = this.placeQueryOutputPort.getById(id)
        place.rating = rating
        this.placeCommandOutputPort.save(place)
        return PlaceDto(place)
    }

    override fun deletePlace(id: Long) {
        TODO("Not yet implemented")
    }
}
