package com.herecity.place.adapter.mariadb

import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.herecity.place.application.port.output.PlaceCommandOutputPort
import com.herecity.place.application.port.output.PlaceQueryOutputPort
import com.herecity.place.domain.entity.Place
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PlaceMariaAdapter(
  val placeRepository: PlaceRepository,
  private val queryFactory: JPAQueryFactory,
) : PlaceQueryOutputPort, PlaceCommandOutputPort {

  override fun save(entity: Place): Place {
    try {
      return this.placeRepository.save(entity)
    } catch (e: DataIntegrityViolationException) {
      throw DuplicateActivityNameException()
    }
  }

  override fun getById(id: Long): Place = this.placeRepository.findById(id).orElseThrow()

  override fun findById(id: Long): Place? = this.placeRepository.findByIdOrNull(id)
}
