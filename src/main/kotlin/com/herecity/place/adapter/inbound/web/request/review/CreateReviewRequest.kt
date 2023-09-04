package com.herecity.place.adapter.inbound.web.request.review

import com.herecity.place.application.port.input.review.CreatePlaceReviewCommand
import org.springframework.web.multipart.MultipartFile
import java.util.UUID
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class CreateReviewRequest(
    val placeId: Long,
    val tourId: Long?,
    @field:Min(1) @field:Max(5)
    val rating: Double,
    val content: String,
    val images: List<MultipartFile>,
) {
    fun toDomain(userId: UUID) = CreatePlaceReviewCommand.In(
        userId = userId,
        placeId = placeId,
        tourId = tourId,
        rating = rating,
        content = content,
        images = images,
    )
}
