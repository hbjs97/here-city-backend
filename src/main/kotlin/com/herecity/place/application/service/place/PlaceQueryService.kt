package com.herecity.place.application.service.place

import com.herecity.place.application.dto.Coordinate2D
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.like.FetchPlaceLikeQuery
import com.herecity.place.application.port.input.place.FetchNearByPlacesPageQuery
import com.herecity.place.application.port.input.place.FetchPlaceQuery
import com.herecity.place.application.port.input.place.FetchPlacesQuery
import com.herecity.place.application.port.input.place.FetchRecommendPlacesPageQuery
import com.herecity.place.application.port.output.place.PlaceQueryOutputPort
import org.locationtech.jts.geom.Coordinate
import org.springframework.stereotype.Service

@Service
class PlaceQueryService(
    private val placeQueryOutputPort: PlaceQueryOutputPort,
    private val fetchPlaceLikeQuery: FetchPlaceLikeQuery,
) : FetchNearByPlacesPageQuery, FetchRecommendPlacesPageQuery, FetchPlacesQuery, FetchPlaceQuery {
    override fun fetchNearByPlacesPage(query: FetchNearByPlacesPageQuery.In): FetchNearByPlacesPageQuery.Out {
        val places = placeQueryOutputPort.searchNearBy(
            regionId = query.regionId,
            activityId = query.activityId,
            unitId = query.unitId,
            offSetPageable = query.offsetPageable,
            placeTypeId = query.placeTypeId,
            name = query.name,
            point = Coordinate2D(query.point),
        )
        val placeLikes = fetchPlaceLikeQuery.fetchPlaceLike(
            FetchPlaceLikeQuery.In(
                userId = query.userId,
                placeId = places.content.map { v -> v.id }
            )
        )
        placeLikes.forEach { v ->
            places.content.find { p -> p.id == v.placeId }?.liked = v.liked
        }
        return FetchNearByPlacesPageQuery.Out(
            places = places.content,
            meta = places.meta
        )
    }

    override fun fetchRecommendPlacesPage(query: FetchRecommendPlacesPageQuery.In): FetchRecommendPlacesPageQuery.Out {
        val places = placeQueryOutputPort.search(
            regionId = query.regionId,
            activityId = query.activityId,
            unitId = query.unitId,
            offSetPageable = query.offsetPageable,
            placeTypeId = query.placeTypeId,
            name = query.name,
        )
        val placeLikes = fetchPlaceLikeQuery.fetchPlaceLike(
            FetchPlaceLikeQuery.In(
                userId = query.userId,
                placeId = places.content.map { v -> v.id }
            )
        )
        placeLikes.forEach { v ->
            places.content.find { p -> p.id == v.placeId }?.liked = v.liked
        }
        return FetchRecommendPlacesPageQuery.Out(
            places = places.content,
            meta = places.meta
        )
    }

    override fun fetchPlace(query: FetchPlaceQuery.In): FetchPlaceQuery.Out =
        placeQueryOutputPort.getById(query.id).let {
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
        val places = placeQueryOutputPort.findAllById(query.ids)
        return FetchPlacesQuery.Out(places = places.map { v -> PlaceDto(v) })
    }
}
