package com.herecity.tour.adapter.input.response

import com.herecity.common.dto.OffsetPageMeta
import com.herecity.tour.application.dto.TourThumbnailDto

data class FetchMyToursResponse(
    val content: List<TourThumbnailDto.Zoned>,
    val meta: OffsetPageMeta,
)
