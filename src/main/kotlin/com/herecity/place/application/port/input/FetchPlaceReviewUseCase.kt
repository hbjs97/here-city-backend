package com.herecity.place.application.port.input

import com.herecity.place.application.dto.GetReviewsDto
import com.herecity.place.application.dto.PlaceReviewDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FetchPlaceReviewUseCase {
    fun getPlaceReviews(getReviewsDto: GetReviewsDto, pageable: Pageable): Page<PlaceReviewDto>
}
