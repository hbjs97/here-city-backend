package com.herecity.tour.application.port.input

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.tour.application.dto.TourThumbnailDto
import java.util.UUID

interface FetchMyToursQuery {
    fun fetchMyTours(query: In): Out
    data class In(
        val userId: UUID,
        val offSetPageable: OffSetPageable,
        val isPast: Boolean? = null,
    )

    data class Out(
        val tours: List<TourThumbnailDto>,
        val meta: OffsetPageMeta,
    )
}
