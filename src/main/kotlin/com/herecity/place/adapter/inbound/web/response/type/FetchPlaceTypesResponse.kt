package com.herecity.place.adapter.inbound.web.response.type

import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceTypeDto

data class FetchPlaceTypesResponse(
    val pages: OffsetPaginated<PlaceTypeDto>,
)
