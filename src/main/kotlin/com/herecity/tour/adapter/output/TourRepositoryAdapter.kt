package com.herecity.tour.adapter.output

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.common.dto.OffsetPaginated
import com.herecity.region.domain.entity.QRegion.region
import com.herecity.tour.application.dto.TourThumbnailDto
import com.herecity.tour.application.port.output.TourOutputPort
import com.herecity.tour.domain.entity.QTour.tour
import com.herecity.tour.domain.entity.QTourist.tourist
import com.herecity.tour.domain.entity.Tour
import com.herecity.tour.domain.vo.Scope
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
class TourRepositoryAdapter(
    private val mariaRepository: TourMariaRepository,
    private val queryFactory: JPAQueryFactory,
) : TourOutputPort {
    override fun findTours(
        offSetPageable: OffSetPageable,
        name: String?,
    ): OffsetPaginated<TourThumbnailDto> {
        val findQuery = queryFactory
            .select(
                Projections.constructor(
                    TourThumbnailDto::class.java,
                    tour.id,
                    tour.name,
                    region.name.`as`("regionName"),
                    Expressions.asBoolean(false).`as`("liked"),
                    tour.from,
                    tour.to,
                    Expressions.asString("").`as`("thumbnail"),
                )
            )
            .from(tour)
            .where(tour.scope.eq(Scope.PUBLIC))
            .where(containsName(name))
            .innerJoin(region).on(tour.regionId.eq(region.id))

        val tours = findQuery.clone()
            .orderBy(tour.rating.desc())
            .offset(offSetPageable.offset())
            .limit(offSetPageable.limit)
            .fetch()
        val count = findQuery.fetchCount()
        return OffsetPaginated(
            content = tours,
            meta = OffsetPageMeta(
                total = count,
                page = offSetPageable.page,
                limit = offSetPageable.limit,
            )
        )
    }

    override fun findMyTours(
        userId: UUID,
        offSetPageable: OffSetPageable,
        isPast: Boolean?,
    ): OffsetPaginated<TourThumbnailDto> {
        val findQuery = queryFactory
            .select(
                Projections.constructor(
                    TourThumbnailDto::class.java,
                    tour.id,
                    tour.name,
                    region.name.`as`("regionName"),
                    Expressions.asBoolean(false).`as`("liked"),
                    tour.from,
                    tour.to,
                    Expressions.asString("").`as`("thumbnail"),
                )
            )
            .from(tourist)
            .where(tourist.userId.eq(userId))
            .innerJoin(region).on(tour.regionId.eq(region.id))
            .innerJoin(tourist.tour)

        when (isPast) {
            false -> {
                findQuery.where(tour.from.gt(LocalDateTime.now()))
                    .orderBy(tour.id.asc())
            }

            true -> {
                findQuery.where(tour.from.lt(LocalDateTime.now()))
                    .orderBy(tour.id.desc())
            }

            else -> {
                findQuery.orderBy(tour.id.desc())
            }
        }

        val findToursQuery = findQuery.clone()
            .offset(offSetPageable.offset())
            .limit(offSetPageable.limit)
            .fetch()
        val count = findQuery.fetchCount()
        return OffsetPaginated(
            content = findToursQuery,
            meta = OffsetPageMeta(
                total = count,
                page = offSetPageable.page,
                limit = offSetPageable.limit,
            )
        )
    }

    override fun getById(id: Long): Tour = mariaRepository.findById(id).orElseThrow()
    override fun findById(id: Long): Tour? = mariaRepository.findById(id).get()
    override fun save(project: Tour): Tour = mariaRepository.save(project)
    private fun containsName(name: String?): BooleanExpression? {
        if (name == null) return null
        return tour.name.contains(name)
    }
}
