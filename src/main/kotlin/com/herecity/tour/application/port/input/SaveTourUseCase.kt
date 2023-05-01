package com.herecity.tour.application.port.input

import com.herecity.tour.application.dto.CreateTourDto
import java.util.*

interface SaveTourUseCase {
  fun createTour(createTourDto: CreateTourDto, createdBy: UUID)
}