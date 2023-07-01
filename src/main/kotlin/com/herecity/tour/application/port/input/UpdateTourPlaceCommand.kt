package com.herecity.tour.application.port.input

import com.herecity.place.application.dto.PlaceDto
import com.herecity.tour.domain.vo.Budget
import java.time.LocalDateTime

interface UpdateTourPlaceCommand {
    fun updateTourPlace(command: In): Out
    data class In(
        val id: Long,
        val placeId: Long,
        val from: LocalDateTime? = null,
        val to: LocalDateTime? = null,
        val budgets: List<Budget> = emptyList(),
    )

    data class Out(
        val place: PlaceDto,
        val from: LocalDateTime,
        val to: LocalDateTime,
        val budgets: List<Budget>,
    )
}
