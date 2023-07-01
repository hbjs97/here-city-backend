package com.herecity.tour.adapter.input.response

import com.herecity.tour.application.dto.TourPlaceDto
import java.time.ZonedDateTime

data class CreateTourResponse(
    val id: Long,
    val ownerName: String,
    val tourName: String,
    val regionName: String,
    val scope: String,
    val from: ZonedDateTime,
    val to: ZonedDateTime,
    val tourPlaces: List<TourPlaceDto>,
)
