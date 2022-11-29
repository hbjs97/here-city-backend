package com.herecity.region.application.port.output

import com.herecity.region.adapter.dto.StreetDto
import com.herecity.region.domain.entity.Street


interface StreetQueryOutputPort {
  fun getStreets(cityId: Long?): List<StreetDto>
  fun getStreetById(id: Long): Street
  fun findStreetById(id: Long): Street?
  fun existsStreetByCityIdAndName(cityId: Long, name: String): Boolean
}
