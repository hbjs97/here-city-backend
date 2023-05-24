package com.herecity.place.application.port.input

import com.herecity.place.application.dto.CreateReviewDto
import com.herecity.place.application.dto.PlaceReviewDto
import java.util.*

interface RecordPlaceReviewUseCase {
    fun review(userId: UUID, createReviewDto: CreateReviewDto): PlaceReviewDto
}
