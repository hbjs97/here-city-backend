package com.herecity.place.adapter.outbound.mariadb.type

import com.herecity.place.domain.entity.PlaceType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaceTypeRepository : JpaRepository<PlaceType, Long> {
    fun existsByName(name: String): Boolean
}
