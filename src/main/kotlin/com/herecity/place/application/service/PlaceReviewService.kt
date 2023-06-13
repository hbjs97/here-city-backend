package com.herecity.place.application.service

import com.herecity.place.application.dto.CreateReviewDto
import com.herecity.place.application.dto.GetReviewsDto
import com.herecity.place.application.dto.PlaceReviewDto
import com.herecity.place.application.port.input.FetchPlaceReviewUseCase
import com.herecity.place.application.port.input.FetchPlaceUseCase
import com.herecity.place.application.port.input.RecordPlaceReviewUseCase
import com.herecity.place.application.port.input.RecordPlaceUseCase
import com.herecity.place.application.port.output.PlaceReviewCommandOutputPort
import com.herecity.place.application.port.output.PlaceReviewQueryOutputPort
import com.herecity.place.domain.entity.PlaceReview
import com.herecity.tour.application.port.input.FetchTourPlanQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class PlaceReviewService(
    private val placeReviewQueryOutputPort: PlaceReviewQueryOutputPort,
    private val placeReviewCommandOutputPort: PlaceReviewCommandOutputPort,
    private val fetchPlaceUseCase: FetchPlaceUseCase,
    private val recordPlaceUseCase: RecordPlaceUseCase,
    private val fetchTourPlanQuery: FetchTourPlanQuery,
) : FetchPlaceReviewUseCase, RecordPlaceReviewUseCase {
    override fun getPlaceReviews(getReviewsDto: GetReviewsDto, pageable: Pageable): Page<PlaceReviewDto> =
        placeReviewQueryOutputPort.fetchReviewsPage(getReviewsDto, pageable)

    @Transactional
    override fun review(userId: UUID, createReviewDto: CreateReviewDto): PlaceReviewDto {
        fetchPlaceUseCase.fetchPlace(createReviewDto.placeId)
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
        ).let {
            PlaceReviewDto(
                id = it.id,
                placeId = it.placeId,
                tourId = it.tourId,
                createdBy = it.createdBy,
                rating = it.rating,
                content = it.content,
                createdAt = it.createdAt,
                images = it.images,
            )
        }

        val ratingAvg = placeReviewCommandOutputPort.getAverageRating(placeId = placeReview.placeId)
        recordPlaceUseCase.savePlaceRating(placeReview.placeId, ratingAvg)
        return placeReview
    }
}
