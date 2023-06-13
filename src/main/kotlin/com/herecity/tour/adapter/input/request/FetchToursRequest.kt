package com.herecity.tour.adapter.input.request

import com.herecity.common.dto.OffSetPageable
import com.herecity.tour.application.port.input.FetchToursQuery

data class FetchToursRequest(
    val name: String?,
) {
    fun toDomain(offSetPageable: OffSetPageable): FetchToursQuery.In = FetchToursQuery.In(
        name = name,
        offSetPageable = offSetPageable,
    )
}
