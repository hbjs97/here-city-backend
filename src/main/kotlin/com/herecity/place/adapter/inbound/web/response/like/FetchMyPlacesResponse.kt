package com.herecity.place.adapter.inbound.web.response.like

import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceDto

data class FetchMyPlacesResponse(
    val content: List<PlaceDto>,
    val meta: OffsetPageMeta,
)
