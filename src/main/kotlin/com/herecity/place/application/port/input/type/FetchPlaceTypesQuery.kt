package com.herecity.place.application.port.input.type

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceTypeDto

interface FetchPlaceTypesQuery {
    fun fetchPlaceTypes(query: In): Out
    data class In(
        val offSetPageable: OffSetPageable,
    )

    data class Out(val pages: OffsetPaginated<PlaceTypeDto>)
}
