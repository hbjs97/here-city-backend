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

  /**
   * @see
   * 상위지역 이름은 중복될 수 없다.
   */
  override fun createUpperRegion(name: String): RegionDto {
    val exist = this.regionQueryOutputPort.existsByName(name)
    if (exist) throw DuplicateRegionNameException()
    val region = this.regionCommandOutputPort.save(Region(name = name))
    return RegionDto(region.id!!, region.name)
  }

  /**
   * @see
   * 하위지역 이름은 같은 상위지역 아래 중복될 수 없다.
   */
  override fun addSubRegion(upperRegionId: Long, name: String): RegionDto {
    val exist = this.regionQueryOutputPort.existsByUpperRegionIdAndName(upperRegionId, name)
    if (exist) throw DuplicateRegionNameException("같은 상위지역 아래 이름이 중복될 수 없습니다.")

    val upperRegion = this.regionQueryOutputPort.getById(upperRegionId)

    val region = this.regionCommandOutputPort.save(Region(upperRegion = upperRegion, name = name))
    return RegionDto(region.id!!, region.name, upperRegion)
  }

  override fun updateRegion(id: Long, updateRegionDto: UpdateRegionDto): RegionDto {
    TODO("Not yet implemented")
  }

  override fun deleteRegion(id: Long) {
    TODO("Not yet implemented")
  }
}
