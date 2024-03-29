package com.herecity.tour.application.service

import com.herecity.tour.application.port.input.FetchMyTourLikesQuery
import com.herecity.tour.application.port.input.FetchTourLikeQuery
import com.herecity.tour.application.port.output.TourLikeQueryOutputPort
import com.herecity.tour.domain.vo.TourLikeId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TourLikeQueryService(
    private val tourLikeQueryOutputPort: TourLikeQueryOutputPort,
) : FetchTourLikeQuery, FetchMyTourLikesQuery {
    override fun fetchTourLike(userId: UUID, tourId: Long): FetchTourLikeQuery.TourLikeDto {
        val tourLike = tourLikeQueryOutputPort.findById(TourLikeId(tourId, userId))
            ?: return FetchTourLikeQuery.TourLikeDto(tourId, false)
        return FetchTourLikeQuery.TourLikeDto(tourId, tourLike.liked())
    }

    override fun fetchTourLike(query: FetchTourLikeQuery.In): List<FetchTourLikeQuery.TourLikeDto> {
        return tourLikeQueryOutputPort.findAllByIds(query.tourId.map { TourLikeId(it, query.userId) })
            .map {
                FetchTourLikeQuery.TourLikeDto(it.tourId, it.liked())
            }
    }

    override fun fetchMyTourLikes(query: FetchMyTourLikesQuery.In): FetchMyTourLikesQuery.Out {
        return tourLikeQueryOutputPort.findMyTourLikes(
            userId = query.userId,
            offSetPageable = query.offSetPageable
        ).let {
            FetchMyTourLikesQuery.Out(
                tours = it.content,
                meta = it.meta,
            )
        }
    }
}
