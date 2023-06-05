package com.herecity.place.adapter.mariadb

import com.herecity.place.application.port.output.PlaceLikeCommandOutputPort
import com.herecity.place.application.port.output.PlaceLikeQueryOutputPort
import com.herecity.place.domain.entity.PlaceLike
import com.herecity.place.domain.vo.PlaceLikeId
import org.springframework.stereotype.Component

@Component
class PlaceLikeMariaAdapter(
    private val placeLikeRepository: PlaceLikeRepository,
) : PlaceLikeQueryOutputPort, PlaceLikeCommandOutputPort {
    override fun save(entity: PlaceLike): PlaceLike = placeLikeRepository.save(entity)
    override fun getById(id: PlaceLikeId): PlaceLike = placeLikeRepository.getById(id)
    override fun findById(id: PlaceLikeId): PlaceLike? = placeLikeRepository.findById(id).orElse(null)
    override fun findAllByIds(ids: List<PlaceLikeId>): List<PlaceLike> = placeLikeRepository.findAllById(ids)
}
