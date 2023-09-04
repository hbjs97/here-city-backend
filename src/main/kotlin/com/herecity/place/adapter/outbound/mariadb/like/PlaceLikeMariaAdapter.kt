package com.herecity.place.adapter.outbound.mariadb.like

import com.herecity.common.dto.OffSetPageable
import com.herecity.common.dto.OffsetPageMeta
import com.herecity.common.dto.OffsetPaginated
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.output.like.PlaceLikeCommandOutputPort
import com.herecity.place.application.port.output.like.PlaceLikeQueryOutputPort
import com.herecity.place.domain.entity.PlaceLike
import com.herecity.place.domain.entity.QPlace.place
import com.herecity.place.domain.entity.QPlaceLike.placeLike
import com.herecity.place.domain.vo.PlaceLikeId
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PlaceLikeMariaAdapter(
    private val placeLikeRepository: PlaceLikeRepository,
    private val queryFactory: JPAQueryFactory,
) : PlaceLikeQueryOutputPort, PlaceLikeCommandOutputPort {
    override fun save(entity: PlaceLike): PlaceLike = placeLikeRepository.save(entity)
    override fun getById(id: PlaceLikeId): PlaceLike = placeLikeRepository.getById(id)
    override fun findById(id: PlaceLikeId): PlaceLike? = placeLikeRepository.findById(id).orElse(null)
    override fun findAllByIds(ids: List<PlaceLikeId>): List<PlaceLike> = placeLikeRepository.findAllById(ids)
    override fun findMyPlaces(userId: UUID, offSetPageable: OffSetPageable): OffsetPaginated<PlaceDto> {
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
            .from(placeLike)
            .where(placeLike.userId.eq(userId))
            .where(placeLike.deletedAt.isNull())
            .innerJoin(place).on(placeLike.placeId.eq(place.id))

        val places = qb.clone()
            .offset(offSetPageable.offset())
            .limit(offSetPageable.limit)
            .fetch()
            .onEach {
                it.liked = true
            }

        val count = qb.clone().fetchCount()

        return OffsetPaginated(
            content = places,
            meta = OffsetPageMeta(
                total = count,
                page = offSetPageable.page,
                limit = offSetPageable.limit,
            )
        )
    }
}
