package com.herecity.place.adapter.input.request

import com.herecity.common.dto.OffSetPageable
import com.herecity.place.application.port.input.FetchReviewsQuery

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
