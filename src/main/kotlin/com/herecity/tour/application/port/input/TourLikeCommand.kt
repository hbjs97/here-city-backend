package com.herecity.tour.application.port.input

import java.util.UUID

interface TourLikeCommand {
    fun like(command: In): Out
    data class In(val tourId: Long, val userId: UUID)
    data class Out(val liked: Boolean)
}
