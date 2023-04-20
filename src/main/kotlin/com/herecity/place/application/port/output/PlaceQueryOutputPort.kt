package com.herecity.place.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.place.application.dto.GetPlacesDto
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.domain.entity.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PlaceQueryOutputPort : BaseQueryRepository<Place, Long> {
  fun search(getPlacesDto: GetPlacesDto, pageable: Pageable): Page<PlaceDto>
}
