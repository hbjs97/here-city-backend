package com.herecity.activity.application.dto

import com.herecity.activity.domain.entity.Activity
import com.querydsl.core.annotations.QueryProjection

data class ActivityDto @QueryProjection constructor(
  val id: Long?,
  val name: String
) {
  companion object {
    fun from(activity: Activity): ActivityDto = ActivityDto(id = activity.id, name = activity.name)

    fun of(id: Long, name: String): ActivityDto = ActivityDto(id = id, name = name)
  }
//  constructor(activity: Activity) : this(id = activity.id, name = activity.name)
}
