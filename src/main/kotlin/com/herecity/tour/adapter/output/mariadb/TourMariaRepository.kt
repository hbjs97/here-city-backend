package com.herecity.tour.adapter.output.mariadb


import com.herecity.tour.domain.entity.Tour
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TourMariaRepository : JpaRepository<Tour, Long>
