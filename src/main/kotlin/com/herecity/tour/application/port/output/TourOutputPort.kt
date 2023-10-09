package com.herecity.tour.application.port.output

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.tour.application.dto.TourThumbnailDto
import com.herecity.tour.domain.entity.Tour
import java.util.UUID

interface TourOutputPort {
    fun findTours(
        offSetPageable: OffSetPageable,
        name: String?,
    ): OffsetPaginated<TourThumbnailDto>

    fun findMyTours(
        userId: UUID,
        offSetPageable: OffSetPageable,
        isPast: Boolean? = null,
    ): OffsetPaginated<TourThumbnailDto>

    fun getById(id: Long): Tour
    fun findById(id: Long): Tour?
    fun findJoinedTourPlaces(tourId: Long, userId: UUID): Tour
    fun save(tour: Tour): Tour
}
