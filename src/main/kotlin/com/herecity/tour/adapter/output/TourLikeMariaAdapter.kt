package com.herecity.tour.adapter.output

import com.herecity.tour.application.port.output.TourLikeCommandOutputPort
import com.herecity.tour.application.port.output.TourLikeQueryOutputPort
import com.herecity.tour.domain.entity.TourLike
import com.herecity.tour.domain.vo.TourLikeId
import org.springframework.stereotype.Component

@Component
class TourLikeMariaAdapter(
    private val tourLikeRepository: TourLikeRepository,
) : TourLikeQueryOutputPort, TourLikeCommandOutputPort {
    override fun save(entity: TourLike): TourLike = tourLikeRepository.save(entity)
    override fun getById(id: TourLikeId): TourLike = tourLikeRepository.getById(id)
    override fun findById(id: TourLikeId): TourLike? = tourLikeRepository.findById(id).orElse(null)
    override fun findAllByIds(ids: List<TourLikeId>): List<TourLike> = tourLikeRepository.findAllById(ids)
}
