package com.herecity.tour.application.port.input

import com.herecity.tour.application.dto.CreateTourPlaceDto
import com.herecity.tour.application.dto.TourNotificationDto
import com.herecity.tour.application.dto.TourPlaceDto
import com.herecity.tour.domain.vo.Scope
import java.time.LocalDateTime
import java.util.UUID

interface CreateTourCommand {
    fun createTour(command: In): Out
    data class In(
        val createdBy: UUID,
        val name: String,
        val regionId: Long,
        val scope: Scope,
        val from: LocalDateTime,
        val to: LocalDateTime,
        val tourPlaces: List<CreateTourPlaceDto>,
        val tourists: Set<UUID> = mutableSetOf(),
    )

    data class Out(
        val id: Long,
        val ownerName: String,
        val tourName: String,
        val regionName: String,
        val scope: String,
        val from: LocalDateTime,
        val to: LocalDateTime,
        val notifications: List<TourNotificationDto>,
        val tourPlaces: List<TourPlaceDto>,
    )
}
