package com.herecity.place.adapter.mariadb

import com.herecity.place.domain.entity.PlaceReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaceReviewRepository : JpaRepository<PlaceReview, Long>
