package com.herecity.region.application.port.output

import com.herecity.region.domain.entity.Region

interface RegionCommandOutputPort {
  fun save(entity: Region): Region
  fun deleteById(id: Long)
}
