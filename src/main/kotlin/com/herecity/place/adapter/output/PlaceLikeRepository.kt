package com.herecity.place.adapter.output

import com.herecity.place.domain.entity.PlaceLike
import com.herecity.place.domain.vo.PlaceLikeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaceLikeRepository : JpaRepository<PlaceLike, PlaceLikeId>
