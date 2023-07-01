package com.herecity.tour.adapter.input.request

import com.herecity.tour.application.port.input.UpdateTourPlaceCommand
import com.herecity.tour.domain.vo.Budget
import java.time.LocalDateTime

data class UpdateTourPlaceRequest(
    val from: LocalDateTime? = null,
    val to: LocalDateTime? = null,
    val budgets: List<Budget> = emptyList(),
) {
    fun toDomain(tourId: Long, placeId: Long) = UpdateTourPlaceCommand.In(
        id = tourId,
        placeId = placeId,
        from = from,
        to = to,
        budgets = budgets,
    )
}
