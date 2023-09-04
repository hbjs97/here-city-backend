package com.herecity.place.application.port.output.type

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceTypeDto
import com.herecity.place.domain.entity.PlaceType

interface PlaceTypeQueryOutputPort : BaseQueryRepository<PlaceType, Long> {
    fun existsByName(name: String): Boolean
    fun findOffSetPageable(offSetPageable: OffSetPageable): OffsetPaginated<PlaceTypeDto>
    fun getByIds(ids: List<Long>): List<PlaceType>
}
