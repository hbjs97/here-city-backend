package com.herecity.tour.application.dto

import java.time.LocalDateTime

data class CreateTourNotification(
  val scheduledAt: LocalDateTime
)
