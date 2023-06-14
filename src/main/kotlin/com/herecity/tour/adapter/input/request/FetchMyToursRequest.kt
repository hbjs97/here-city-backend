package com.herecity.tour.adapter.input.request

import com.herecity.common.dto.OffSetPageable
import com.herecity.tour.application.port.input.FetchMyToursQuery
import java.util.UUID

data class FetchMyToursRequest(
    val isPast: Boolean?,
) {
    fun toDomain(userId: UUID, offSetPageable: OffSetPageable): FetchMyToursQuery.In = FetchMyToursQuery.In(
        userId = userId,
        offSetPageable = offSetPageable,
        isPast = isPast,
    )
}
