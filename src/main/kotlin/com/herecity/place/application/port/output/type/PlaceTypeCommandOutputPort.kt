package com.herecity.place.application.port.output.type

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.place.domain.entity.PlaceType

interface PlaceTypeCommandOutputPort : BaseCommandRepository<PlaceType, Long> {
    fun deleteById(id: Long)
}
