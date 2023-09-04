package com.herecity.place.application.port.output.place

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.place.domain.entity.Place

interface PlaceCommandOutputPort : BaseCommandRepository<Place, Long>
