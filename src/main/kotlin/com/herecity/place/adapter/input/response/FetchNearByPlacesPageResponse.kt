package com.herecity.place.adapter.input.response

import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceDto

data class FetchNearByPlacesPageResponse(
    val content: List<PlaceDto>,
    val meta: OffsetPageMeta,
)
