package com.herecity.place.application.port.output.place

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.Coordinate2D
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.domain.entity.Place

interface PlaceQueryOutputPort : BaseQueryRepository<Place, Long> {
    fun searchNearBy(
        regionId: Long?,
        activityId: List<Long>,
        unitId: List<Long>,
        offSetPageable: OffSetPageable,
        point: Coordinate2D,
        placeTypeId: Long?,
        name: String?,
    ): OffsetPaginated<PlaceDto>

    fun search(
        regionId: Long?,
        activityId: List<Long>,
        unitId: List<Long>,
        offSetPageable: OffSetPageable,
        placeTypeId: Long?,
        name: String?,
    ): OffsetPaginated<PlaceDto>

    fun findAllById(ids: List<Long>): List<Place>
}
