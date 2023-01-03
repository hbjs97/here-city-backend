package com.herecity.activity.adapter.mariadb

import com.herecity.activity.adapter.dto.SearchActivityDto
import com.herecity.activity.application.dto.ActivityDto
import com.herecity.activity.application.dto.QActivityDto
import com.herecity.activity.application.port.output.ActivityCommandOutputPort
import com.herecity.activity.application.port.output.ActivityQueryOutputPort
import com.herecity.activity.domain.entity.Activity
import com.herecity.activity.domain.entity.QActivity.activity
import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.util.StringUtils
import com.querydsl.jpa.impl.JPAQueryFactory
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component
import org.webjars.NotFoundException

private val logger = KotlinLogging.logger {}

@Component
class ActivityMariaAdapter(
  private val activityRepository: ActivityRepository,
  private val queryFactory: JPAQueryFactory
) : ActivityQueryOutputPort, ActivityCommandOutputPort {
  override fun search(searchActivityDto: SearchActivityDto): List<ActivityDto> = this.queryFactory
    .select(QActivityDto(activity.id, activity.name))
    .from(activity)
    .where(this.containsName(searchActivityDto.name))
    .fetch()

  override fun getById(id: Long): Activity = this.activityRepository.findById(id).orElseThrow()

  override fun findById(id: Long): Activity? = this.activityRepository.findById(id).get()

  override fun save(activity: Activity): Activity {
    try {
      return this.activityRepository.save(activity)
    } catch (e: DataIntegrityViolationException) {
      throw DuplicateActivityNameException()
    }
  }

  override fun findByName(name: String): Activity? = this.activityRepository.findByName(name)
  override fun getByIds(ids: List<Long>): List<Activity> {
    val activities = this.activityRepository.findAllById(ids)
    val notFoundedIds = ids.subtract(activities.map { v -> v.id }.toSet())
    if (notFoundedIds.isNotEmpty()) throw NotFoundException("id가 존재하지 않습니다. [${notFoundedIds}]")
    return activities
  }

  override fun deleteById(id: Long) {
    val activity = this.getById(id)
    this.activityRepository.delete(activity)
  }

  private fun containsName(name: String?): BooleanExpression? {
    if (!StringUtils.isNullOrEmpty(name)) return activity.name.contains(name)
    return null
  }
}
