package com.herecity.place.adapter.input.response

import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceReviewDto

data class FetchMyReviewsResponse(
    val content: List<PlaceReviewDto>,
    val meta: OffsetPageMeta,
)
