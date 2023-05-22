package com.herecity.tour.application.dto

import com.herecity.tour.domain.vo.Scope
import java.time.LocalDateTime
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Max

data class CreateTourDto(
    @field:Max(50)
    val name: String,
    val regionId: Long,
    val scope: Scope,
    val from: LocalDateTime,
    val to: LocalDateTime,
    @field:Valid
    val tourPlaces: List<CreateTourPlaceDto>,
    val tourists: List<UUID> = listOf(),
    val notifications: List<CreateTourNotification>,
)
