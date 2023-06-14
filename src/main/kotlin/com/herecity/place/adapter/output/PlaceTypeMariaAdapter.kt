package com.herecity.place.adapter.output

import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.herecity.place.application.port.output.PlaceTypeCommandOutputPort
import com.herecity.place.application.port.output.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.PlaceType
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.webjars.NotFoundException

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

    override fun existsByName(name: String): Boolean = this.placeTypeRepository.existsByName(name)
    override fun findByPage(pageable: Pageable): Page<PlaceType> = this.placeTypeRepository.findAll(pageable)
    override fun getByIds(ids: List<Long>): List<PlaceType> {
        val placeTypes = this.placeTypeRepository.findAllById(ids)
        val notFoundedIds = ids.subtract(placeTypes.map { v -> v.id }.toSet())
        if (notFoundedIds.isNotEmpty()) throw NotFoundException("id가 존재하지 않습니다. [$notFoundedIds]")
        return placeTypes
    }

    override fun deleteById(id: Long) = this.placeTypeRepository.deleteById(id)
}
