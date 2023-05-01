package com.herecity.tour.application.dto

import com.herecity.place.application.dto.PlaceDto
import com.herecity.tour.domain.entity.TourPlace
import java.time.LocalDateTime

data class TourPlaceDto(
  val place: PlaceDto,
  val from: LocalDateTime,
  val to: LocalDateTime,
  val budget: Int,
  val description: String,
) {
  constructor(tourPlace: TourPlace, place: PlaceDto) : this(
    place = place,
    from = tourPlace.from,
    to = tourPlace.to,
    budget = tourPlace.budget,
    description = tourPlace.description,
  )
}
