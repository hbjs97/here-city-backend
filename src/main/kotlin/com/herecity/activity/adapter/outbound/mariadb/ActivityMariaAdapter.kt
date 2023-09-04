package com.herecity.activity.adapter.outbound.mariadb

import com.herecity.activity.application.port.output.ActivityCommandOutputPort
import com.herecity.activity.application.port.output.ActivityQueryOutputPort
import com.herecity.activity.domain.entity.Activity
import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component
import org.webjars.NotFoundException

@Component
class ActivityMariaAdapter(
    private val activityRepository: ActivityRepository,
    private val queryFactory: JPAQueryFactory,
) : ActivityQueryOutputPort, ActivityCommandOutputPort {
    override fun search(name: String?): List<Activity> {
        return if (name == null) {
            return this.activityRepository.findAll()
        } else {
            this.activityRepository.findByNameContaining(name)
        }
    }

    override fun getById(id: Long): Activity = this.activityRepository.findById(id).orElseThrow()

    override fun findById(id: Long): Activity? = this.activityRepository.findById(id).get()

    override fun save(entity: Activity): Activity {
        try {
            return this.activityRepository.save(entity)
        } catch (e: DataIntegrityViolationException) {
            throw DuplicateActivityNameException()
        }
    }

    override fun findByName(name: String): Activity? = this.activityRepository.findByName(name)
    override fun getByIds(ids: List<Long>): List<Activity> {
        val activities = this.activityRepository.findAllById(ids)
        val notFoundedIds = ids.subtract(activities.map { v -> v.id }.toSet())
        if (notFoundedIds.isNotEmpty()) throw NotFoundException("id가 존재하지 않습니다. [$notFoundedIds]")
        return activities
    }
}
