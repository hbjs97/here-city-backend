package com.herecity.place.application.port.output.like

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.domain.entity.PlaceLike
import com.herecity.place.domain.vo.PlaceLikeId
import java.util.UUID

interface PlaceLikeQueryOutputPort : BaseQueryRepository<PlaceLike, PlaceLikeId> {
    fun findAllByUserIdAndPlaceIdIn(userId: UUID, placeId: List<Long>): List<PlaceLike>
    fun findMyPlaces(userId: UUID, offSetPageable: OffSetPageable): OffsetPaginated<PlaceDto>
}
