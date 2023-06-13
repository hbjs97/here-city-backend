package com.herecity.tour.adapter.input.response

import com.herecity.tour.application.dto.TourNotificationDto
import com.herecity.tour.application.dto.TourPlaceDto
import java.time.LocalDateTime

data class FetchTourPlanResponse(
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
