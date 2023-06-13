package com.herecity.tour.application.port.input

import com.herecity.tour.application.dto.TourNotificationDto
import com.herecity.tour.application.dto.TourPlaceDto
import java.time.LocalDateTime

interface FetchTourPlanQuery {
    /**
     * public 하게 공개되는 투어일정
     * 단순 일정만 리턴한다.
     */
    fun fetchTourPlan(query: In): Out
    data class In(val id: Long)
    data class Out(
        val id: Long,
        val ownerName: String,
        val tourName: String,
        val regionName: String,
        val scope: String,
        val from: LocalDateTime,
        val to: LocalDateTime,
        val notifications: List<TourNotificationDto> = listOf(),
        val tourPlaces: List<TourPlaceDto> = listOf(),
    )
}
