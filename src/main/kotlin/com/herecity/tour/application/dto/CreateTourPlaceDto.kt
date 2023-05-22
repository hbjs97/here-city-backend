package com.herecity.tour.application.dto

import com.herecity.tour.domain.vo.Budget
import java.time.LocalDateTime
import javax.validation.Valid

data class CreateTourPlaceDto(
    val placeId: Long,
    val from: LocalDateTime,
    val to: LocalDateTime,
    @field:Valid
    val budgets: List<Budget> = emptyList(),
    val description: String,
)
