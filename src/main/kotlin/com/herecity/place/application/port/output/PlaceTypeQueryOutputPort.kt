package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.place.domain.entity.PlaceType

interface PlaceTypeQueryOutputPort : BaseQueryRepository<PlaceType, Long>
