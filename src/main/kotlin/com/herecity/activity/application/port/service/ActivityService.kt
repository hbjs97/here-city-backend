package com.herecity.activity.application.port.service

import com.herecity.activity.adapter.dto.SearchActivityDto
import com.herecity.activity.application.dto.ActivityDto
import com.herecity.activity.application.port.input.LoadActivityUseCase
import com.herecity.activity.application.port.input.RecordActivityUseCase
import com.herecity.activity.application.port.output.ActivityCommandOutputPort
import com.herecity.activity.application.port.output.ActivityQueryOutputPort
import com.herecity.activity.domain.entity.Activity
import com.herecity.activity.domain.exception.DuplicateActivityNameException
import org.springframework.stereotype.Service

@Service
class ActivityService(private val activityQueryOutputPort: ActivityQueryOutputPort, private val activityCommandOutputPort: ActivityCommandOutputPort) : LoadActivityUseCase, RecordActivityUseCase {
  override fun search(searchActivityDto: SearchActivityDto): List<ActivityDto> = this.activityQueryOutputPort.search(searchActivityDto)

  override fun createActivity(name: String): ActivityDto {
    val exist = this.activityQueryOutputPort.findByName(name) !== null
    if (exist) throw DuplicateActivityNameException()
    val activity = this.activityCommandOutputPort.save(Activity(name = name))
    return ActivityDto.from(activity)
  }

  override fun updateActivity(id: Long, name: String): ActivityDto {
    val activity = this.activityQueryOutputPort.getById(id)
    activity.name = name
    this.activityCommandOutputPort.save(activity)
    return ActivityDto.from(activity)
  }

  override fun deleteActivity(id: Long) = this.activityCommandOutputPort.deleteById(id)

}
