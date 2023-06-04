package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.place.domain.entity.PlaceLike
import com.herecity.place.domain.vo.PlaceLikeId

interface PlaceLikeCommandOutputPort : BaseCommandRepository<PlaceLike, PlaceLikeId>
