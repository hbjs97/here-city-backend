package com.herecity.tour.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.tour.application.dto.TourThumbnailDto
import com.herecity.tour.domain.entity.TourLike
import com.herecity.tour.domain.vo.TourLikeId
import java.util.UUID

interface TourLikeQueryOutputPort : BaseQueryRepository<TourLike, TourLikeId> {
    fun findAllByIds(ids: List<TourLikeId>): List<TourLike>
    fun findMyTourLikes(userId: UUID, offSetPageable: OffSetPageable): OffsetPaginated<TourThumbnailDto>
}
