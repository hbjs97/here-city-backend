package com.herecity.place.adapter.mariadb

import com.herecity.place.domain.entity.Place
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaceRepository : JpaRepository<Place, Long>
