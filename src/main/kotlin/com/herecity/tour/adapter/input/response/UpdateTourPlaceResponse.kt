package com.herecity.tour.adapter.input.response

import com.herecity.place.application.dto.PlaceDto
import com.herecity.tour.domain.vo.Budget
import java.time.ZonedDateTime

data class UpdateTourPlaceResponse(
    val place: PlaceDto,
    val from: ZonedDateTime,
    val to: ZonedDateTime,
    val budgets: List<Budget>,
)
