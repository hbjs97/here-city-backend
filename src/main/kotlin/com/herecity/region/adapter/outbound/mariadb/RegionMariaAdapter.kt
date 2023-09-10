package com.herecity.region.adapter.outbound.mariadb

import com.herecity.region.application.port.outbound.RegionCommandOutputPort
import com.herecity.region.application.port.outbound.RegionQueryOutputPort
import com.herecity.region.domain.entity.Region
import org.springframework.stereotype.Component

@Component
class RegionMariaAdapter(
    private val regionRepository: RegionRepository,
) : RegionQueryOutputPort, RegionCommandOutputPort {
    override fun save(entity: Region): Region = regionRepository.save(entity)

    override fun getRegions(): List<Region> = regionRepository.findAll()

    override fun findById(id: Long): Region? = regionRepository.findById(id).get()

    override fun findByName(name: String): Region? = regionRepository.findByName(name)

    override fun existsByName(name: String): Boolean = regionRepository.existsByName(name)

    override fun getById(id: Long): Region = regionRepository.findById(id).orElseThrow()
}
