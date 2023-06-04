package com.herecity.place.application.port.input

import java.util.*

interface PlaceDisLikeCommand {
    fun disLike(command: In): Out
    data class In(val placeId: Long, val userId: UUID)
    data class Out(val liked: Boolean)
}
