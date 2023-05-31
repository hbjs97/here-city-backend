package com.herecity.place.adapter.mariadb

import com.herecity.place.application.dto.GetReviewsDto
import com.herecity.place.application.dto.PlaceReviewDto
import com.herecity.place.application.port.output.PlaceReviewCommandOutputPort
import com.herecity.place.application.port.output.PlaceReviewQueryOutputPort
import com.herecity.place.domain.entity.PlaceReview
import com.herecity.place.domain.entity.QPlaceReview.placeReview
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

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

    override fun fetchReviewsPage(getReviewsDto: GetReviewsDto, pageable: Pageable): Page<PlaceReviewDto> {
        val qb = queryFactory
            .selectFrom(placeReview)
            .where(
                eqPlaceId(getReviewsDto.placeId),
                eqTourId(getReviewsDto.tourId),
            )
        val pageReviews = qb
            .orderBy(placeReview.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
            .map {
                PlaceReviewDto(
                    id = it.id,
                    placeId = it.placeId,
                    tourId = it.tourId,
                    createdBy = it.createdBy,
                    rating = it.rating,
                    content = it.content,
                    createdAt = it.createdAt,
                    images = it.images,
                )
            }
        val count = qb.fetchCount()
        return PageImpl(pageReviews, pageable, count)
    }

    override fun getById(id: Long): PlaceReview {
        6
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
}
