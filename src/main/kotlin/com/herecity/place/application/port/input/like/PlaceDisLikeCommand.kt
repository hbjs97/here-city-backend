package com.herecity.place.application.port.input.like

import java.util.UUID

interface PlaceDisLikeCommand {
    fun disLike(command: In): Out
    data class In(val placeId: Long, val userId: UUID)
    data class Out(val liked: Boolean)
}
