package com.herecity.tour.application.port.input

import com.herecity.tour.application.dto.CreateTourDto
import com.herecity.tour.application.dto.TourPlaceDto
import com.herecity.tour.application.dto.TourPlanDto
import com.herecity.tour.application.dto.UpdateTourDto
import com.herecity.tour.application.dto.UpdateTourPlaceDto
import java.util.UUID

interface SaveTourUseCase {
    fun createTour(createTourDto: CreateTourDto, createdBy: UUID): TourPlanDto
    fun updateTour(id: Long, updateTourDto: UpdateTourDto): TourPlanDto
    fun updateTourPlace(id: Long, placeId: Long, updateTourPlaceDto: UpdateTourPlaceDto): TourPlaceDto
}
