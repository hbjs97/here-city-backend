package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.domain.entity.Place
import org.locationtech.jts.geom.Point

interface PlaceQueryOutputPort : BaseQueryRepository<Place, Long> {
    fun search(
        regionId: Long?,
        activityId: List<Long>,
        unitId: List<Long>,
        offSetPageable: OffSetPageable,
        placeTypeId: Long?,
        name: String?,
        point: Point?,
    ): OffsetPaginated<PlaceDto>

    fun findAllById(ids: List<Long>): List<Place>
}
