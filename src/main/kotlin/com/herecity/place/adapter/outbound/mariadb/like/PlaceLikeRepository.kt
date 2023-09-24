package com.herecity.place.adapter.outbound.mariadb.like

import com.herecity.place.domain.entity.PlaceLike
import com.herecity.place.domain.vo.PlaceLikeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlaceLikeRepository : JpaRepository<PlaceLike, PlaceLikeId> {
    fun findAllByUserIdAndPlaceIdIn(userId: UUID, placeId: List<Long>): List<PlaceLike>
}
