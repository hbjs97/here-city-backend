package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.place.application.dto.GetReviewsDto
import com.herecity.place.application.dto.PlaceReviewDto
import com.herecity.place.domain.entity.PlaceReview
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PlaceReviewQueryOutputPort : BaseQueryRepository<PlaceReview, Long> {
    fun fetchReviewsPage(getReviewsDto: GetReviewsDto, pageable: Pageable): Page<PlaceReviewDto>
}
