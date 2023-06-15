package com.herecity.tour.adapter.output

import com.herecity.tour.domain.entity.TourLike
import com.herecity.tour.domain.vo.TourLikeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TourLikeRepository : JpaRepository<TourLike, TourLikeId>
