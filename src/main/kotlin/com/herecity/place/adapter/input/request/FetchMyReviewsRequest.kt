package com.herecity.place.adapter.input.request

import com.herecity.common.dto.OffSetPageable
import com.herecity.place.application.port.input.FetchMyReviewsQuery
import java.util.UUID

data class FetchMyReviewsRequest(
    val placeId: Long? = null,
    val tourId: Long? = null,
) {
    fun toDomain(userId: UUID, offSetPageable: OffSetPageable) = FetchMyReviewsQuery.In(
        userId = userId,
        placeId = placeId,
        tourId = tourId,
        offSetPageable = offSetPageable,
    )
}
