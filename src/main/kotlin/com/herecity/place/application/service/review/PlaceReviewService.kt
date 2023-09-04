package com.herecity.place.application.service.review

import com.herecity.place.application.port.input.place.FetchPlaceQuery
import com.herecity.place.application.port.input.place.SavePlaceRatingCommand
import com.herecity.place.application.port.input.review.CreatePlaceReviewCommand
import com.herecity.place.application.port.input.review.FetchMyReviewsQuery
import com.herecity.place.application.port.input.review.FetchReviewsQuery
import com.herecity.place.application.port.output.review.PlaceReviewCommandOutputPort
import com.herecity.place.application.port.output.review.PlaceReviewQueryOutputPort
import com.herecity.place.domain.entity.PlaceReview
import com.herecity.s3.S3ClientAdapter
import com.herecity.s3.core.UploadObject
import com.herecity.tour.application.port.input.FetchTourPlanQuery
import com.herecity.user.application.port.input.FetchUserUseCase
import dev.wimcorp.common.util.FileUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class PlaceReviewService(
    private val placeReviewQueryOutputPort: PlaceReviewQueryOutputPort,
    private val placeReviewCommandOutputPort: PlaceReviewCommandOutputPort,
    private val fetchPlaceQuery: FetchPlaceQuery,
    private val fetchTourPlanQuery: FetchTourPlanQuery,
    private val fetchUserUseCase: FetchUserUseCase,
    private val s3ClientAdapter: S3ClientAdapter,
    private val savePlaceRatingCommand: SavePlaceRatingCommand,
) : FetchReviewsQuery, FetchMyReviewsQuery, CreatePlaceReviewCommand {
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
    override fun review(command: CreatePlaceReviewCommand.In): CreatePlaceReviewCommand.Out {
        fetchPlaceQuery.fetchPlace(FetchPlaceQuery.In(command.placeId))
        command.tourId?.let {
            fetchTourPlanQuery.fetchTourPlan(
                FetchTourPlanQuery.In(id = it)
            )
        }

        val uploadedImages = s3ClientAdapter.upload(command.images.map {
            UploadObject(
                objectKey = "${
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                }-${UUID.randomUUID()}.${FileUtils.extractExtension(it.originalFilename)}",
                file = it,
            )
        })

        val placeReview = placeReviewCommandOutputPort.save(
            PlaceReview(
                createdBy = command.userId,
                placeId = command.placeId,
                tourId = command.tourId,
                rating = command.rating,
                content = command.content,
                images = uploadedImages.map { it.url }
            )
        )
        val user = fetchUserUseCase.fetchUser(command.userId)
        val ratingAvg = placeReviewCommandOutputPort.getAverageRating(placeId = placeReview.placeId)
        savePlaceRatingCommand.savePlaceRating(SavePlaceRatingCommand.In(id = placeReview.placeId, rating = ratingAvg))
        return placeReview.let {
            CreatePlaceReviewCommand.Out(
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
