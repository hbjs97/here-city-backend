package com.herecity.place.adapter.inbound.web.response.review

import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceReviewDto

data class FetchMyReviewsResponse(
    val content: List<PlaceReviewDto>,
    val meta: OffsetPageMeta,
)
