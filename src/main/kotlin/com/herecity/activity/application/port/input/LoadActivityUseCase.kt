package com.herecity.activity.application.port.input

import com.herecity.activity.adapter.dto.SearchActivityDto
import com.herecity.activity.application.dto.ActivityDto

interface LoadActivityUseCase {
  fun search(searchActivityDto: SearchActivityDto): List<ActivityDto>
}
