package com.herecity.region.application.service

import com.herecity.region.application.port.inbound.CreateRegionCommand
import com.herecity.region.application.port.inbound.UpdateRegionCommand
import com.herecity.region.application.port.outbound.RegionCommandOutputPort
import com.herecity.region.application.port.outbound.RegionQueryOutputPort
import com.herecity.region.domain.entity.Region
import com.herecity.region.domain.exception.DuplicateRegionNameException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegionCommandService(
    private val regionQueryOutputPort: RegionQueryOutputPort,
    private val regionCommandOutputPort: RegionCommandOutputPort,
) :
    CreateRegionCommand, UpdateRegionCommand {
    @Transactional
    override fun createRegion(command: CreateRegionCommand.In): CreateRegionCommand.Out {
        val exist = this.regionQueryOutputPort.existsByName(command.name)
        if (exist) throw DuplicateRegionNameException()
        val region = this.regionCommandOutputPort.save(Region(name = command.name))
        return CreateRegionCommand.Out(region.id, region.name)
    }

    @Transactional
    override fun updateRegion(command: UpdateRegionCommand.In): UpdateRegionCommand.Out {
        val region = this.regionQueryOutputPort.getById(command.id)
        if (command.name !== null) {
            region.name = command.name
        }
        this.regionCommandOutputPort.save(region)
        return UpdateRegionCommand.Out(region.id, region.name)
    }
}
