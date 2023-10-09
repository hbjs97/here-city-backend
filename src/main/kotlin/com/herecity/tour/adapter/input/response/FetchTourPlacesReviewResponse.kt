package com.herecity.tour.adapter.input.response

import com.herecity.tour.application.port.input.FetchTourPlacesReviewQuery
import java.time.ZonedDateTime

data class FetchTourPlacesReviewResponse(
    val name: String,
    val from: ZonedDateTime,
    val to: ZonedDateTime,
    val reviews: List<FetchTourPlacesReviewQuery.PlaceReview>,
) {
    companion object {
        fun from(payload: FetchTourPlacesReviewQuery.Out): FetchTourPlacesReviewResponse =
            FetchTourPlacesReviewResponse(
                name = payload.name,
                from = payload.from,
                to = payload.to,
                reviews = payload.reviews,
            )
    }
}
