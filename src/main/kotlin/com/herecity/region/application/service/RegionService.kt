package com.herecity.region.application.service

import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.application.dto.UpdateRegionDto
import com.herecity.region.application.port.input.LoadRegionUseCase
import com.herecity.region.application.port.input.RecordRegionUseCase
import com.herecity.region.application.port.output.RegionCommandOutputPort
import com.herecity.region.application.port.output.RegionQueryOutputPort
import com.herecity.region.domain.entity.Region
import com.herecity.region.domain.exception.DuplicateRegionNameException
import org.springframework.stereotype.Service

@Service
class RegionService(
  private val regionQueryOutputPort: RegionQueryOutputPort,
  private val regionCommandOutputPort: RegionCommandOutputPort
) :
  LoadRegionUseCase, RecordRegionUseCase {
  override fun getUpperRegions(): List<RegionDto> = this.regionQueryOutputPort.getUpperRegions()

  override fun getSubRegions(upperRegionId: Long): List<RegionDto> = this.regionQueryOutputPort.getSubRegions(upperRegionId)

  override fun createUpperRegion(name: String): RegionDto {
    val exist = this.regionQueryOutputPort.existsByName(name)
    if (exist) throw DuplicateRegionNameException()
    val region = this.regionCommandOutputPort.save(Region(name = name))
    return RegionDto(region.id!!, region.name)
  }

  override fun addSubRegion(upperRegionId: Long, name: String): RegionDto {
    TODO("Not yet implemented")
  }

  override fun updateRegion(id: Long, updateRegionDto: UpdateRegionDto): RegionDto {
    TODO("Not yet implemented")
  }

  override fun deleteRegion(id: Long) {
    TODO("Not yet implemented")
  }
}
