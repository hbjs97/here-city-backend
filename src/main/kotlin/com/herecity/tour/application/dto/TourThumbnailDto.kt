package com.herecity.tour.application.dto

import java.time.LocalDateTime

data class TourThumbnailDto(
    val id: Long,
    val name: String,
    val regionName: String,
    val liked: Boolean,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val thumbnail: String?,
)
