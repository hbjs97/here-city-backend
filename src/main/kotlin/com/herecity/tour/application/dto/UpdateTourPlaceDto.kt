package com.herecity.tour.application.dto

import com.herecity.tour.domain.vo.Budget
import java.time.LocalDateTime

data class UpdateTourPlaceDto(
    val from: LocalDateTime? = null,
    val to: LocalDateTime? = null,
    val budgets: List<Budget> = emptyList(),
)
