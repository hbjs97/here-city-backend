package com.herecity.tour.application.dto

import com.herecity.tour.domain.vo.Scope
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.Max

data class CreateTourDto(
  val name: @Max(50) String,
  val regionId: Long,
  val scope: Scope,
  val from: LocalDateTime,
  val to: LocalDateTime,
  val tourPlaces: List<CreateTourPlaceDto>,
  val tourists: List<UUID> = listOf(),
  val notifications: List<CreateTourNotification>
)
