package com.herecity.place.application.port.input

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceReviewDto

interface FetchReviewsQuery {
    fun fetchReviews(query: In): Out
    data class In(
        val offSetPageable: OffSetPageable,
        val placeId: Long? = null,
        val tourId: Long? = null,
    )

    data class Out(
        val reviews: List<PlaceReviewDto>,
        val meta: OffsetPageMeta,
    )
}
