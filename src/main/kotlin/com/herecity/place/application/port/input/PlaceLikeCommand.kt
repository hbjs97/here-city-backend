package com.herecity.place.application.port.input

import java.util.*

interface PlaceLikeCommand {
    fun like(command: In): Out
    data class In(val placeId: Long, val userId: UUID)
    data class Out(val liked: Boolean)
}
