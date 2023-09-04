package com.herecity.place.application.service.like

import com.herecity.place.application.dto.Coordinate2D
import com.herecity.place.application.port.input.like.FetchMyPlacesQuery
import com.herecity.place.application.port.input.like.FetchPlaceLikeQuery
import com.herecity.place.application.port.output.like.PlaceLikeQueryOutputPort
import com.herecity.place.domain.service.DistanceCalculator
import com.herecity.place.domain.vo.PlaceLikeId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PlaceLikeQueryService(
    private val placeLikeQueryOutputPort: PlaceLikeQueryOutputPort,
    private val calculator: DistanceCalculator,
) : FetchPlaceLikeQuery, FetchMyPlacesQuery {
    override fun fetchPlaceLike(userId: UUID, placeId: Long): FetchPlaceLikeQuery.PlaceLikeDto {
        val placeLike = placeLikeQueryOutputPort.findById(PlaceLikeId(placeId, userId))
            ?: return FetchPlaceLikeQuery.PlaceLikeDto(placeId, false)
        return FetchPlaceLikeQuery.PlaceLikeDto(placeId, placeLike.liked())
    }

    override fun fetchPlaceLike(query: FetchPlaceLikeQuery.In): List<FetchPlaceLikeQuery.PlaceLikeDto> {
        return placeLikeQueryOutputPort.findAllByIds(query.placeId.map { PlaceLikeId(it, query.userId) })
            .map {
                FetchPlaceLikeQuery.PlaceLikeDto(it.placeId, it.liked())
            }
    }

    override fun fetchMyPlaces(query: FetchMyPlacesQuery.In): FetchMyPlacesQuery.Out {
        return placeLikeQueryOutputPort.findMyPlaces(
            userId = query.userId,
            offSetPageable = query.offSetPageable,
        ).let {
            it.content.forEach { place ->
                if (query.x == null || query.y == null) {
                    return@forEach
                }
                place.distance = calculator.measure(
                    Coordinate2D(query.x, query.y),
                    Coordinate2D(place.point.x, place.point.y)
                )
            }
            FetchMyPlacesQuery.Out(
                places = it.content,
                meta = it.meta,
            )
        }
    }
}
