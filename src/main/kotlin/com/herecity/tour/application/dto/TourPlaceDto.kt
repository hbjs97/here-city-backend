package com.herecity.tour.application.dto

import com.herecity.place.application.dto.PlaceDto
import com.herecity.tour.domain.entity.TourPlace
import com.herecity.tour.domain.vo.Budget
import java.time.LocalDateTime

data class TourPlaceDto(
    val place: PlaceDto,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val budgets: List<Budget>,
) {
    constructor(tourPlace: TourPlace, place: PlaceDto) : this(
        place = place,
        from = tourPlace.from,
        to = tourPlace.to,
        budgets = tourPlace.budgets,
    )
}
