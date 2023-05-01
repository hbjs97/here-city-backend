package com.herecity.tour.application.dto

import java.time.LocalDateTime

data class CreateTourPlaceDto(
  val placeId: Long,
  val from: LocalDateTime,
  val to: LocalDateTime,
  val budget: Int,
  val description: String,
)
