package com.herecity.place.adapter.mariadb

import com.herecity.place.application.port.output.PlaceCommandOutputPort
import com.herecity.place.application.port.output.PlaceQueryOutputPort
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class PlaceMariaAdapter(
  private val placeRepository: PlaceRepository,
  private val queryFactory: JPAQueryFactory,
) : PlaceQueryOutputPort, PlaceCommandOutputPort {
}
