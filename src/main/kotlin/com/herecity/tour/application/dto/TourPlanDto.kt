package com.herecity.tour.application.dto

import java.time.LocalDateTime

data class TourPlanDto(
  val ownerName: String,
  val tourName: String,
  val regionName: String,
  val scope: String,
  val from: LocalDateTime,
  val to: LocalDateTime,
  val notifications: List<TourNotificationDto> = listOf(),
  val tourPlaces: List<TourPlaceDto> = listOf()
)