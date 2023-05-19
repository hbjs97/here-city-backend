package com.herecity.tour.application.dto

import java.time.LocalDateTime

data class UpdateTourPlaceDto(
  val from: LocalDateTime? = null,
  val to: LocalDateTime? = null,
  val budget: Int? = null,
  val description: String? = null,
)
