package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseCommandRepository
import com.herecity.place.domain.entity.PlaceReview

interface PlaceReviewCommandOutputPort : BaseCommandRepository<PlaceReview, Long> {
    fun getAverageRating(placeId: Long): Double
}
