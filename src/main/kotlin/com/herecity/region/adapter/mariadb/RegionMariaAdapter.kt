package com.herecity.region.adapter.mariadb

import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.application.port.output.RegionCommandOutputPort
import com.herecity.region.application.port.output.RegionQueryOutputPort
import com.herecity.region.domain.entity.QRegion.region
import com.herecity.region.domain.entity.Region
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class RegionMariaAdapter(
  private val regionRepository: RegionRepository,
  private val queryFactory: JPAQueryFactory
) : RegionQueryOutputPort, RegionCommandOutputPort {


  override fun save(entity: Region): Region = this.regionRepository.save(entity)

  override fun deleteById(id: Long) = this.regionRepository.deleteById(id);

  override fun getUpperRegions(): List<RegionDto> = this.queryFactory
    .select(
      Projections.constructor(
        RegionDto::class.java,
        region.id,
        region.name
      )
    )
    .from(region)
    .where(this.isNullUpperRegionId())
    .fetch()


  override fun getSubRegions(id: Long): List<RegionDto> = this.queryFactory
    .select(
      Projections.constructor(
        RegionDto::class.java,
        region.id,
        region.name,
        region.upperRegion.id.`as`("upperRegionId"),
        region.upperRegion.name.`as`("upperRegionName")
      )
    )
    .from(region)
    .innerJoin(region.upperRegion)
    .where(this.eqUpperRegionId(id))
    .fetch()


  override fun findById(id: Long): Region? = this.regionRepository.findById(id).get()

  override fun findByName(name: String): Region? = this.regionRepository.findByName(name)

  override fun existsByName(name: String): Boolean = this.regionRepository.existsByName(name)
  
  override fun existsByUpperRegionIdAndName(upperRegionId: Long, name: String): Boolean = this.regionRepository.existsByUpperRegionIdAndName(upperRegionId, name)

  override fun getById(id: Long): Region = this.regionRepository.findById(id).orElseThrow()

  private fun eqUpperRegionId(upperRegionId: Long?): BooleanExpression? {
    if (upperRegionId == null) return null
    return region.upperRegionId.eq(upperRegionId)
  }

  private fun isNullUpperRegionId(): BooleanExpression {
    return region.upperRegionId.isNull
  }
}
