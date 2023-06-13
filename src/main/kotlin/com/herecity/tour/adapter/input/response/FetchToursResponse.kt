package com.herecity.tour.adapter.input.response

import com.herecity.common.dto.OffsetPageMeta
import com.herecity.tour.application.port.input.FetchToursQuery

data class FetchToursResponse(
    val content: List<FetchToursQuery.TourThumbnail>,
    val meta: OffsetPageMeta,
)
