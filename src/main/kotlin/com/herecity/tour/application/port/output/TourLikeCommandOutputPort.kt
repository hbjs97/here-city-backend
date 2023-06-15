package com.herecity.tour.application.port.output

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.tour.domain.entity.TourLike
import com.herecity.tour.domain.vo.TourLikeId

interface TourLikeCommandOutputPort : BaseCommandRepository<TourLike, TourLikeId>
