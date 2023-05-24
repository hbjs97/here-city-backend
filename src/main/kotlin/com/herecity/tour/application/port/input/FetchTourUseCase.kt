package com.herecity.tour.application.port.input

import com.herecity.tour.application.dto.TourPlanDto

interface FetchTourUseCase {
    /**
     * public 하게 공개되는 투어일정
     * 단순 일정만 리턴한다.
     */
    fun fetchTourPlan(id: Long): TourPlanDto
}
