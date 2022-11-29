package com.herecity.region.adapter.mariadb

import com.herecity.region.domain.entity.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : JpaRepository<City, Long> {
  fun findByName(name: String): City?
  fun existsByName(name: String): Boolean
}
