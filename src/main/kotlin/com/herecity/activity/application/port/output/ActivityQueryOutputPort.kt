package com.herecity.activity.application.port.output

import com.herecity.activity.adapter.dto.SearchActivityDto
import com.herecity.activity.application.dto.ActivityDto
import com.herecity.activity.domain.entity.Activity
import com.herecity.common.adapter.mariadb.BaseQueryRepository

interface ActivityQueryOutputPort : BaseQueryRepository<Activity, Long> {
  fun search(searchActivityDto: SearchActivityDto): List<ActivityDto>
  fun findByName(name: String): Activity?
  fun getByIds(ids: List<Long>): List<Activity>
}
