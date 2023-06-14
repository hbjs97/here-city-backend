package com.herecity.place.adapter.output

import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.herecity.place.application.dto.GetPlacesDto
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.output.PlaceCommandOutputPort
import com.herecity.place.application.port.output.PlaceQueryOutputPort
import com.herecity.place.domain.entity.Place
import com.herecity.place.domain.entity.QPlace.place
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.util.StringUtils
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PlaceMariaAdapter(
    private val placeRepository: PlaceRepository,
    private val queryFactory: JPAQueryFactory,
) : PlaceQueryOutputPort, PlaceCommandOutputPort {

    override fun save(entity: Place): Place {
        try {
            return this.placeRepository.save(entity)
        } catch (e: DataIntegrityViolationException) {
            throw DuplicateActivityNameException()
        }
    }

    override fun search(getPlacesDto: GetPlacesDto, pageable: Pageable): Page<PlaceDto> {
        val qb = this.queryFactory
            .select(
                Projections.constructor(
                    PlaceDto::class.java,
                    place.id,
                    place.title,
                    place.name,
                    place.description,
                    place.address,
                    place.point,
                    place.rating,
                    place.images,
                )
            )
            .from(place)
            .where(this.containsName(getPlacesDto.name))

        if (getPlacesDto.name != null) {
            qb.where(place.name.contains(getPlacesDto.name).or(place.title.contains(getPlacesDto.name)))
        }

        if (getPlacesDto.activityId.isNotEmpty()) {
            qb.innerJoin(place.placeActivities).where(
                place.placeActivities.any().activity.id.`in`(getPlacesDto.activityId)
            )
        }

        if (getPlacesDto.placeTypeId != null) {
            qb.innerJoin(place.placeTypes).where(place.placeTypes.any().type.id.eq(getPlacesDto.placeTypeId))
        }

        if (getPlacesDto.unitId.isNotEmpty()) {
            qb.innerJoin(place.placeUnits).where(place.placeUnits.any().unit.id.`in`(getPlacesDto.unitId))
        }

        val places = qb
            .orderBy(place.rating.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .distinct()
            .fetch()

        val count = qb.fetchCount()

        return PageImpl(places, pageable, count)
    }

    override fun findAllById(ids: List<Long>): List<Place> = placeRepository.findAllById(ids)

    override fun getById(id: Long): Place = this.placeRepository.findById(id).orElseThrow()

    override fun findById(id: Long): Place? = this.placeRepository.findByIdOrNull(id)

    private fun containsName(name: String?): BooleanExpression? {
        if (!StringUtils.isNullOrEmpty(name)) return place.name.contains(name)
        return null
    }
}
