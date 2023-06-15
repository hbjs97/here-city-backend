package com.herecity.tour.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.tour.domain.entity.TourLike
import com.herecity.tour.domain.vo.TourLikeId

interface TourLikeQueryOutputPort : BaseQueryRepository<TourLike, TourLikeId> {
    fun findAllByIds(ids: List<TourLikeId>): List<TourLike>
}
