package com.herecity.tour.application.port.output

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.tour.application.port.input.FetchToursQuery
import com.herecity.tour.domain.entity.Tour

interface TourOutputPort {
    fun findTours(
        offSetPageable: OffSetPageable,
        name: String?,
    ): OffsetPaginated<FetchToursQuery.TourThumbnail>

    fun getById(id: Long): Tour
    fun findById(id: Long): Tour?
    fun save(tour: Tour): Tour
}
