package com.herecity.place.application.service

import com.herecity.place.application.port.input.FetchMyPlacesQuery
import com.herecity.place.application.port.input.FetchPlaceLikeQuery
import com.herecity.place.application.port.output.PlaceLikeQueryOutputPort
import com.herecity.place.domain.service.DistanceCalculator
import com.herecity.place.domain.vo.PlaceLikeId
import com.herecity.place.domain.vo.PositionVO
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
            it.content.forEach {
                if (query.x == null || query.y == null) {
                    return@forEach
                }
                it.distance = calculator.measure(PositionVO(query.x, query.y), PositionVO(it.point))
            }
            FetchMyPlacesQuery.Out(
                places = it.content,
                meta = it.meta,
            )
        }
    }
}
