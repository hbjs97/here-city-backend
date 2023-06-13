package com.herecity.tour.application.port.input

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta

interface FetchToursQuery {
    fun fetchTours(query: In): Out
    data class In(
        val offSetPageable: OffSetPageable,
        val name: String? = null,
    )

    data class Out(
        val tours: List<TourThumbnail>,
        val meta: OffsetPageMeta,
    )

    data class TourThumbnail(
        val id: Long,
        val name: String,
        val regionName: String,
        val liked: Boolean,
        val thumbnail: String?,
    )
}
