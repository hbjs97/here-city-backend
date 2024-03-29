package com.herecity.place.adapter.outbound.mariadb.review

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceReviewDto
import com.herecity.place.application.port.output.review.PlaceReviewCommandOutputPort
import com.herecity.place.application.port.output.review.PlaceReviewQueryOutputPort
import com.herecity.place.domain.entity.PlaceReview
import com.herecity.place.domain.entity.QPlace.place
import com.herecity.place.domain.entity.QPlaceReview.placeReview
import com.herecity.tour.application.port.input.FetchTourPlacesReviewQuery
import com.herecity.tour.domain.entity.QTourPlace.tourPlace
import com.herecity.user.domain.entity.QUser.user
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PlaceReviewMariaAdapter(
    private val placeReviewRepository: PlaceReviewRepository,
    private val queryFactory: JPAQueryFactory,
) : PlaceReviewQueryOutputPort, PlaceReviewCommandOutputPort {
    override fun getAverageRating(placeId: Long): Double =
        queryFactory
            .select(placeReview.rating.avg())
            .from(placeReview)
            .where(placeReview.placeId.eq(placeId))
            .fetchOne()
            .let { return it ?: 0.0 }

    override fun save(entity: PlaceReview): PlaceReview = placeReviewRepository.save(entity)

    override fun fetchReviews(
        offSetPageable: OffSetPageable,
        userId: UUID?,
        placeId: Long?,
        tourId: Long?,
    ): OffsetPaginated<PlaceReviewDto> {
        val qb = queryFactory
            .select(
                Projections.constructor(
                    PlaceReviewDto::class.java,
                    placeReview.id,
                    placeReview.placeId,
                    placeReview.tourId,
                    placeReview.rating,
                    placeReview.content,
                    placeReview.images,
                    placeReview.createdAt,
                    placeReview.createdBy,
                    user.displayName.`as`("userDisplayName"),
                    user.thumbnail.`as`("userThumbnail"),
                )
            )
            .from(placeReview)
            .where(
                eqPlaceId(placeId),
                eqTourId(tourId),
                eqCreatedBy(userId),
            )
            .innerJoin(user).on(user.id.eq(placeReview.createdBy))
            .innerJoin(place).on(place.id.eq(placeReview.placeId))

        val placeReviews = qb.clone()
            .orderBy(placeReview.id.desc())
            .offset(offSetPageable.offset())
            .limit(offSetPageable.limit)
            .fetch()
        val count = qb.fetchCount()

        return OffsetPaginated(
            content = placeReviews,
            meta = OffsetPageMeta(
                total = count,
                page = offSetPageable.page,
                limit = offSetPageable.limit,
            )
        )
    }

    override fun findTourPlaceReviews(tourId: Long): List<FetchTourPlacesReviewQuery.PlaceReview> {
        return queryFactory
            .select(
                Projections.constructor(
                    FetchTourPlacesReviewQuery.PlaceReview::class.java,
                    tourPlace.placeId,
                    place.title,
                    place.name,
                    placeReview.rating,
                    placeReview.content,
                    placeReview.images,
                )
            )
            .from(tourPlace)
            .innerJoin(place).on(place.id.eq(tourPlace.placeId))
            .leftJoin(placeReview).on(placeReview.placeId.eq(tourPlace.placeId))
            .where(tourPlace.tour.id.eq(tourId))
            .groupBy(tourPlace.placeId)
            .fetch()
    }

    override fun getById(id: Long): PlaceReview {
        return placeReviewRepository.getById(id)
    }

    override fun findById(id: Long): PlaceReview? {
        return placeReviewRepository.findById(id).orElse(null)
    }

    private fun eqPlaceId(placeId: Long?): BooleanExpression? {
        if (placeId != null) return placeReview.placeId.eq(placeId)
        return null
    }

    private fun eqTourId(tourId: Long?): BooleanExpression? {
        if (tourId != null) return placeReview.tourId.eq(tourId)
        return null
    }

    private fun eqCreatedBy(userId: UUID?): BooleanExpression? {
        if (userId != null) return placeReview.createdBy.eq(userId)
        return null
    }
}
