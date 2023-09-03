package com.herecity.region.adapter.mariadb

import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.application.port.output.RegionCommandOutputPort
import com.herecity.region.application.port.output.RegionQueryOutputPort
import com.herecity.region.domain.entity.QRegion.region
import com.herecity.region.domain.entity.Region
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class RegionMariaAdapter(
    private val regionRepository: RegionRepository,
    private val queryFactory: JPAQueryFactory,
) : RegionQueryOutputPort, RegionCommandOutputPort {
    override fun save(entity: Region): Region = this.regionRepository.save(entity)

    override fun getRegions(): List<RegionDto> = this.queryFactory
        .select(
            Projections.constructor(
                RegionDto::class.java,
                region.id,
                region.name
            )
        )
        .from(region)
        .fetch()

    override fun findById(id: Long): Region? = this.regionRepository.findById(id).get()

    override fun findByName(name: String): Region? = this.regionRepository.findByName(name)

    override fun existsByName(name: String): Boolean = this.regionRepository.existsByName(name)

    override fun getById(id: Long): Region = this.regionRepository.findById(id).orElseThrow()
}
