package com.herecity.tour.application.port.input

import java.time.ZonedDateTime
import java.util.UUID

interface FetchTourPlacesReviewQuery {
    fun fetchReviews(query: In): Out
    data class In(val tourId: Long, val userId: UUID)
    data class Out(
        val name: String,
        val from: ZonedDateTime,
        val to: ZonedDateTime,
        val reviews: List<PlaceReview>,
    )

    data class PlaceReview(
        val placeId: Long,
        val title: String,
        val name: String,
        val rating: Double?,
        val content: String?,
        val images: List<String>?,
    )
}
