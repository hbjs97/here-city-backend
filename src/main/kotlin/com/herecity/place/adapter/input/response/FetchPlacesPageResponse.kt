package com.herecity.place.adapter.input.response

import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceDto

data class FetchPlacesPageResponse(
    val content: List<PlaceDto>,
    val meta: OffsetPageMeta,
)
