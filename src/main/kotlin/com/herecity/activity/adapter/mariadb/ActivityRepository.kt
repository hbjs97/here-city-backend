package com.herecity.activity.adapter.mariadb

import com.herecity.activity.domain.entity.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : JpaRepository<Activity, Long> {
    fun findByName(name: String): Activity?
    fun findByNameContaining(name: String): List<Activity>
}
