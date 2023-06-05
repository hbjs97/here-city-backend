package com.herecity.place.application.service

import com.herecity.place.application.port.input.FetchPlaceLikeQuery
import com.herecity.place.application.port.output.PlaceLikeQueryOutputPort
import com.herecity.place.domain.vo.PlaceLikeId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PlaceLikeQueryService(
    private val placeLikeQueryOutputPort: PlaceLikeQueryOutputPort,
) : FetchPlaceLikeQuery {
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
}
