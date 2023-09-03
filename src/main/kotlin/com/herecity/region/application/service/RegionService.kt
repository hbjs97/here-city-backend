package com.herecity.region.application.service

import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.application.dto.UpdateRegionDto
import com.herecity.region.application.port.input.FetchRegionUseCase
import com.herecity.region.application.port.input.RecordRegionUseCase
import com.herecity.region.application.port.output.RegionCommandOutputPort
import com.herecity.region.application.port.output.RegionQueryOutputPort
import com.herecity.region.domain.entity.Region
import com.herecity.region.domain.exception.DuplicateRegionNameException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegionService(
    private val regionQueryOutputPort: RegionQueryOutputPort,
    private val regionCommandOutputPort: RegionCommandOutputPort,
) :
    FetchRegionUseCase, RecordRegionUseCase {
    override fun getRegions(): List<RegionDto> = this.regionQueryOutputPort.getRegions()

    override fun getById(id: Long): RegionDto {
        val region = this.regionQueryOutputPort.getById(id)
        return RegionDto(region)
    }

    override fun createRegion(name: String): RegionDto {
        val exist = this.regionQueryOutputPort.existsByName(name)
        if (exist) throw DuplicateRegionNameException()
        val region = this.regionCommandOutputPort.save(Region(name = name))
        return RegionDto(region.id, region.name)
    }

    override fun updateRegion(id: Long, updateRegionDto: UpdateRegionDto): RegionDto {
        val region = this.regionQueryOutputPort.getById(id)
        if (updateRegionDto.upperRegionId !== null) {
            region.upperRegion = this.regionQueryOutputPort.getById(updateRegionDto.upperRegionId)
        }
        this.regionCommandOutputPort.save(region)
        return RegionDto(region)
    }

    @Transactional
    override fun deleteRegion(id: Long) {
        this.regionQueryOutputPort.getById(id).apply { delete() }
    }
}
