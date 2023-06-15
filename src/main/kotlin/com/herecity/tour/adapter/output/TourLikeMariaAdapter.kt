package com.herecity.tour.adapter.output

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.common.dto.OffsetPaginated
import com.herecity.region.domain.entity.QRegion.region
import com.herecity.tour.application.dto.TourThumbnailDto
import com.herecity.tour.application.port.output.TourLikeCommandOutputPort
import com.herecity.tour.application.port.output.TourLikeQueryOutputPort
import com.herecity.tour.domain.entity.QTour.tour
import com.herecity.tour.domain.entity.QTourLike.tourLike
import com.herecity.tour.domain.entity.TourLike
import com.herecity.tour.domain.vo.TourLikeId
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TourLikeMariaAdapter(
    private val tourLikeRepository: TourLikeRepository,
    private val queryFactory: JPAQueryFactory,
) : TourLikeQueryOutputPort, TourLikeCommandOutputPort {
    override fun save(entity: TourLike): TourLike = tourLikeRepository.save(entity)
    override fun getById(id: TourLikeId): TourLike = tourLikeRepository.getById(id)
    override fun findById(id: TourLikeId): TourLike? = tourLikeRepository.findById(id).orElse(null)
    override fun findAllByIds(ids: List<TourLikeId>): List<TourLike> = tourLikeRepository.findAllById(ids)
    override fun findMyTourLikes(userId: UUID, offSetPageable: OffSetPageable): OffsetPaginated<TourThumbnailDto> {
        val qb = this.queryFactory
            .select(
                Projections.constructor(
                    TourThumbnailDto::class.java,
                    tour.id,
                    tour.name,
                    region.name.`as`("upperRegionName"),
                    Expressions.asBoolean(true).`as`("liked"),
                    tour.from,
                    tour.to,
                    Expressions.asString("").`as`("thumbnail"),
                )
            )
            .from(tourLike)
            .where(tourLike.userId.eq(userId), tourLike.deletedAt.isNull())
            .innerJoin(tour).on(tourLike.tourId.eq(tour.id))
            .innerJoin(region).on(tour.regionId.eq(region.id))

        val tours = qb.clone()
            .orderBy(tourLike.createdAt.desc())
            .offset(offSetPageable.offset())
            .limit(offSetPageable.limit)
            .fetch()

        val count = qb.clone().fetchCount()

        return OffsetPaginated(
            content = tours,
            meta = OffsetPageMeta(
                total = count,
                page = offSetPageable.page,
                limit = offSetPageable.limit,
            )
        )
    }
}
