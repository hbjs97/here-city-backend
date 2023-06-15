package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceReviewDto
import com.herecity.place.domain.entity.PlaceReview
import java.util.UUID

interface PlaceReviewQueryOutputPort : BaseQueryRepository<PlaceReview, Long> {
    fun fetchReviews(
        offSetPageable: OffSetPageable,
        userId: UUID?,
        placeId: Long?,
        tourId: Long?,
    ): OffsetPaginated<PlaceReviewDto>
}
