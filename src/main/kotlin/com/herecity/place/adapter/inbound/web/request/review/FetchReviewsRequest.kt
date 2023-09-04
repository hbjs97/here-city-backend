package com.herecity.place.adapter.inbound.web.request.review

import com.herecity.common.dto.OffSetPageable
import com.herecity.place.application.port.input.review.FetchReviewsQuery

data class FetchReviewsRequest(
    val placeId: Long? = null,
    val tourId: Long? = null,
) {
    fun toDomain(offSetPageable: OffSetPageable) = FetchReviewsQuery.In(
        placeId = placeId,
        tourId = tourId,
        offSetPageable = offSetPageable,
    )
}
