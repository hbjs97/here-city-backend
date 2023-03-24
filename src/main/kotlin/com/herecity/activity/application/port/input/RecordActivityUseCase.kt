package com.herecity.activity.application.port.input

import com.herecity.activity.application.dto.ActivityDto

interface RecordActivityUseCase {
  fun createActivity(name: String): ActivityDto
  fun updateActivity(id: Long, name: String): ActivityDto
  fun deleteActivity(id: Long)
}
