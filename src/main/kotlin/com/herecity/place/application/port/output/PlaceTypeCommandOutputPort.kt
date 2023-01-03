package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.place.domain.entity.PlaceType

interface PlaceTypeCommandOutputPort : BaseCommandRepository<PlaceType, Long> {
}
