package com.herecity.place.adapter.mariadb

import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.herecity.place.application.port.output.PlaceTypeCommandOutputPort
import com.herecity.place.application.port.output.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.PlaceType
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PlaceTypeMariaAdapter(
  val placeTypeRepository: PlaceTypeRepository,
  private val queryFactory: JPAQueryFactory,
) : PlaceTypeQueryOutputPort, PlaceTypeCommandOutputPort {

  override fun save(entity: PlaceType): PlaceType {
    try {
      return this.placeTypeRepository.save(entity)
    } catch (e: DataIntegrityViolationException) {
      throw DuplicateActivityNameException()
    }
  }

  override fun getById(id: Long): PlaceType = this.placeTypeRepository.findById(id).orElseThrow()

  override fun findById(id: Long): PlaceType? = this.placeTypeRepository.findByIdOrNull(id)

}
