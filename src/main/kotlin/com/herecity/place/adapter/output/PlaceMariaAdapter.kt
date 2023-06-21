package com.herecity.place.adapter.output

import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.output.PlaceCommandOutputPort
import com.herecity.place.application.port.output.PlaceQueryOutputPort
import com.herecity.place.domain.entity.Place
import com.herecity.place.domain.entity.QPlace.place
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.util.StringUtils
import com.querydsl.jpa.impl.JPAQueryFactory
import org.locationtech.jts.geom.Point
import org.springframework.dao.DataIntegrityViolationException
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

    override fun search(
        regionId: Long?,
        activityId: List<Long>,
        unitId: List<Long>,
        offSetPageable: OffSetPageable,
        placeTypeId: Long?,
        name: String?,
        point: Point?,
    ): OffsetPaginated<PlaceDto> {
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
            .where(this.eqRegionId(regionId))
            .where(this.containsName(name))

        if (name != null) {
            qb.where(place.name.contains(name).or(place.title.contains(name)))
        }

        if (activityId.isNotEmpty()) {
            qb.innerJoin(place.placeActivities).where(
                place.placeActivities.any().activity.id.`in`(activityId)
            )
        }

        if (placeTypeId != null) {
            qb.innerJoin(place.placeTypes).where(place.placeTypes.any().type.id.eq(placeTypeId))
        }

        if (unitId.isNotEmpty()) {
            qb.innerJoin(place.placeUnits).where(place.placeUnits.any().unit.id.`in`(unitId))
        }

        val places = qb
            .clone()
            .orderBy(place.rating.desc())
            .offset(offSetPageable.offset())
            .limit(offSetPageable.limit)
            .distinct()
            .fetch()

        val count = qb.fetchCount()
        return OffsetPaginated(
            content = places,
            meta = OffsetPageMeta(
                total = count,
                page = offSetPageable.page,
                limit = offSetPageable.limit,
            )
        )
    }

    override fun findAllById(ids: List<Long>): List<Place> = placeRepository.findAllById(ids)

    override fun getById(id: Long): Place = this.placeRepository.findById(id).orElseThrow()

    override fun findById(id: Long): Place? = this.placeRepository.findByIdOrNull(id)

    private fun eqRegionId(regionId: Long?): BooleanExpression? {
        if (regionId != null) return place.regionId.eq(regionId)
        return null
    }

    private fun containsName(name: String?): BooleanExpression? {
        if (!StringUtils.isNullOrEmpty(name)) return place.name.contains(name)
        return null
    }
}
