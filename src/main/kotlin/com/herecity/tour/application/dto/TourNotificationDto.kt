package com.herecity.tour.application.dto

import com.herecity.tour.domain.entity.TourNotification
import java.time.LocalDateTime

data class TourNotificationDto(
  val scheduledAt: LocalDateTime,
  val sendedAt: LocalDateTime? = null,
) {
  constructor(tourNotification: TourNotification) : this(
    scheduledAt = tourNotification.scheduledAt,
    sendedAt = tourNotification.sendedAt
  )
}
