package com.herecity.place.application.service

import com.herecity.place.application.port.input.CreatePlaceReviewCommand
import com.herecity.place.application.port.input.FetchMyReviewsQuery
import com.herecity.place.application.port.input.FetchPlaceQuery
import com.herecity.place.application.port.input.FetchReviewsQuery
import com.herecity.place.application.port.input.RecordPlaceUseCase
import com.herecity.place.application.port.output.PlaceReviewCommandOutputPort
import com.herecity.place.application.port.output.PlaceReviewQueryOutputPort
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
    private val recordPlaceUseCase: RecordPlaceUseCase,
    private val fetchTourPlanQuery: FetchTourPlanQuery,
    private val fetchUserUseCase: FetchUserUseCase,
    private val s3ClientAdapter: S3ClientAdapter,
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
        recordPlaceUseCase.savePlaceRating(placeReview.placeId, ratingAvg)
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
