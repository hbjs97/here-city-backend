package com.herecity.place.adapter.outbound.mariadb.type

import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceTypeDto
import com.herecity.place.application.port.output.type.PlaceTypeCommandOutputPort
import com.herecity.place.application.port.output.type.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.PlaceType
import com.herecity.place.domain.entity.QPlaceType.placeType
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.webjars.NotFoundException

@Component
class PlaceTypeMariaAdapter(
    private val placeTypeRepository: PlaceTypeRepository,
    private val queryFactory: JPAQueryFactory,
) : PlaceTypeQueryOutputPort, PlaceTypeCommandOutputPort {

    override fun save(entity: PlaceType): PlaceType {
        try {
            return placeTypeRepository.save(entity)
        } catch (e: DataIntegrityViolationException) {
            throw DuplicateActivityNameException()
        }
    }

    override fun getById(id: Long): PlaceType = placeTypeRepository.findById(id).orElseThrow()

    override fun findById(id: Long): PlaceType? = placeTypeRepository.findByIdOrNull(id)

    override fun existsByName(name: String): Boolean = placeTypeRepository.existsByName(name)
    override fun findOffSetPageable(offSetPageable: OffSetPageable): OffsetPaginated<PlaceTypeDto> {
        val query = queryFactory.selectFrom(placeType)
            .offset(offSetPageable.offset())
            .limit(offSetPageable.limit)
        val placeTypes = query.fetch()
        val count = placeTypes.size.toLong()
        return OffsetPaginated(
            content = placeTypes.map { v -> PlaceTypeDto(id = v.id, name = v.name) },
            meta = OffsetPageMeta(
                total = count,
                page = offSetPageable.page,
                limit = offSetPageable.limit,
            )
        )
    }

    override fun getByIds(ids: List<Long>): List<PlaceType> {
        val placeTypes = placeTypeRepository.findAllById(ids)
        val notFoundedIds = ids.subtract(placeTypes.map { v -> v.id }.toSet())
        if (notFoundedIds.isNotEmpty()) throw NotFoundException("id가 존재하지 않습니다. [$notFoundedIds]")
        return placeTypes
    }

    override fun deleteById(id: Long) = placeTypeRepository.deleteById(id)
}
