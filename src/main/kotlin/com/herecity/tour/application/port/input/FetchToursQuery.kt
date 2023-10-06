package com.herecity.tour.application.port.input

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.tour.application.dto.TourThumbnailDto

interface FetchToursQuery {
    fun fetchTours(query: In): Out
    data class In(
        val offSetPageable: OffSetPageable,
        val name: String? = null,
    )

    data class Out(
        val tours: List<TourThumbnailDto.Zoned>,
        val meta: OffsetPageMeta,
    )
}
