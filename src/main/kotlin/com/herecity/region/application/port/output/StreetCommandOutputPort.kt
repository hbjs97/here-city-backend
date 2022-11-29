package com.herecity.region.application.port.output

import com.herecity.region.domain.entity.Street

interface StreetCommandOutputPort {
  fun saveStreet(entity: Street): Street
  fun deleteStreetById(id: Long)
}
