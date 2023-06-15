package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceReviewDto
import com.herecity.place.domain.entity.PlaceReview

interface PlaceReviewQueryOutputPort : BaseQueryRepository<PlaceReview, Long> {
    fun fetchReviews(
        offSetPageable: OffSetPageable,
        placeId: Long?,
        tourId: Long?,
    ): OffsetPaginated<PlaceReviewDto>
}
