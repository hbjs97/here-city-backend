package com.herecity.tour.application.port.input

import com.herecity.tour.application.dto.TourNotificationDto
import com.herecity.tour.application.dto.TourPlaceDto
import com.herecity.tour.domain.vo.Scope
import java.time.LocalDateTime

interface UpdateTourCommand {
    fun updateTour(command: In): Out
    data class In(
        val id: Long,
        val name: String? = null,
        val scope: Scope? = null,
        val from: LocalDateTime? = null,
        val to: LocalDateTime? = null,
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
