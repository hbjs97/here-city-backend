package com.herecity.place.application.service

import com.herecity.place.application.dto.CreateReviewDto
import com.herecity.place.application.dto.PlaceReviewDto
import com.herecity.place.application.port.input.FetchMyReviewsQuery
import com.herecity.place.application.port.input.FetchPlaceQuery
import com.herecity.place.application.port.input.FetchReviewsQuery
import com.herecity.place.application.port.input.RecordPlaceReviewUseCase
import com.herecity.place.application.port.input.RecordPlaceUseCase
import com.herecity.place.application.port.output.PlaceReviewCommandOutputPort
import com.herecity.place.application.port.output.PlaceReviewQueryOutputPort
import com.herecity.place.domain.entity.PlaceReview
import com.herecity.tour.application.port.input.FetchTourPlanQuery
import com.herecity.user.application.port.input.FetchUserUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class PlaceReviewService(
    private val placeReviewQueryOutputPort: PlaceReviewQueryOutputPort,
    private val placeReviewCommandOutputPort: PlaceReviewCommandOutputPort,
    private val fetchPlaceQuery: FetchPlaceQuery,
    private val recordPlaceUseCase: RecordPlaceUseCase,
    private val fetchTourPlanQuery: FetchTourPlanQuery,
    private val fetchUserUseCase: FetchUserUseCase,
) : FetchReviewsQuery, FetchMyReviewsQuery, RecordPlaceReviewUseCase {
    override fun fetchReviews(query: FetchReviewsQuery.In): FetchReviewsQuery.Out {
        return placeReviewQueryOutputPort.fetchReviews(
            offSetPageable = query.offSetPageable,
            userId = null,
            placeId = query.placeId,
            tourId = query.tourId,
        ).let {
            FetchReviewsQuery.Out(
                reviews = it.content,
                meta = it.meta,
            )
        }
    }

    override fun fetchMyReviews(query: FetchMyReviewsQuery.In): FetchMyReviewsQuery.Out {
        return placeReviewQueryOutputPort.fetchReviews(
            offSetPageable = query.offSetPageable,
            userId = query.userId,
            placeId = query.placeId,
            tourId = query.tourId,
        ).let {
            FetchMyReviewsQuery.Out(
                reviews = it.content,
                meta = it.meta,
            )
        }
    }

    @Transactional
    override fun review(userId: UUID, createReviewDto: CreateReviewDto): PlaceReviewDto {
        fetchPlaceQuery.fetchPlace(FetchPlaceQuery.In(createReviewDto.placeId))
        createReviewDto.tourId?.let {
            fetchTourPlanQuery.fetchTourPlan(
                FetchTourPlanQuery.In(id = it)
            )
        }

        val placeReview = placeReviewCommandOutputPort.save(
            PlaceReview(
                createdBy = userId,
                placeId = createReviewDto.placeId,
                tourId = createReviewDto.tourId,
                rating = createReviewDto.rating,
                content = createReviewDto.content,
                images = createReviewDto.images,
            )
        )
        val user = fetchUserUseCase.fetchUser(userId)
        val ratingAvg = placeReviewCommandOutputPort.getAverageRating(placeId = placeReview.placeId)
        recordPlaceUseCase.savePlaceRating(placeReview.placeId, ratingAvg)
        return placeReview.let {
            PlaceReviewDto(
                id = it.id,
                placeId = it.placeId,
                rating = it.rating,
                tourId = it.tourId,
                content = it.content,
                createdAt = it.createdAt,
                images = it.images,
                createdBy = it.createdBy,
                userDisplayName = user.displayName,
                userThumbnail = user.thumbnail,
            )
        }
    }
}
