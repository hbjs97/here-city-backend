package com.herecity.tour.application.port.input

import java.util.UUID

interface FetchTourLikeQuery {
    fun fetchTourLike(userId: UUID, tourId: Long): TourLikeDto
    fun fetchTourLike(query: In): List<TourLikeDto>
    data class In(val userId: UUID, val tourId: List<Long>)
    data class TourLikeDto(val tourId: Long, val liked: Boolean)
}
