package com.herecity.place.adapter.inbound.web.response.place

import com.herecity.common.dto.OffsetPageMeta
import com.herecity.place.application.dto.PlaceDto

data class FetchNearByPlacesPageResponse(
    val content: List<PlaceDto>,
    val meta: OffsetPageMeta,
)
