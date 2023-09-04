package com.herecity.place.application.port.input.like

import java.util.UUID

interface FetchPlaceLikeQuery {
    fun fetchPlaceLike(userId: UUID, placeId: Long): PlaceLikeDto
    fun fetchPlaceLike(query: In): List<PlaceLikeDto>
    data class In(val userId: UUID, val placeId: List<Long>)
    data class PlaceLikeDto(val placeId: Long, val liked: Boolean)
}
