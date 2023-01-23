package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.place.domain.entity.PlaceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PlaceTypeQueryOutputPort : BaseQueryRepository<PlaceType, Long> {
  fun existsByName(name: String): Boolean
  fun findByPage(pageable: Pageable): Page<PlaceType>
}
