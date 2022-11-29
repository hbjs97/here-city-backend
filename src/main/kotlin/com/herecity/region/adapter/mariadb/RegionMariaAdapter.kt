package com.herecity.region.adapter.mariadb

import com.herecity.region.adapter.dto.CityDto
import com.herecity.region.adapter.dto.StreetDto
import com.herecity.region.application.port.output.CityCommandOutputPort
import com.herecity.region.application.port.output.CityQueryOutputPort
import com.herecity.region.application.port.output.StreetCommandOutputPort
import com.herecity.region.application.port.output.StreetQueryOutputPort
import com.herecity.region.domain.entity.City
import com.herecity.region.domain.entity.QStreet.street
import com.herecity.region.domain.entity.Street
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import mu.KotlinLogging
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class RegionMariaAdapter(
  private val cityRepository: CityRepository,
  private val streetRepository: StreetRepository,
  private val queryFactory: JPAQueryFactory
) : CityQueryOutputPort, CityCommandOutputPort, StreetQueryOutputPort, StreetCommandOutputPort {

//  override fun search(searchRegionDto: SearchRegionDto): List<RegionDto> = this.queryFactory
//    .select(
//      Projections.constructor(
//        RegionDto::class.java,
//        region.id,
//        region.name,
//        region.unit.id.`as`("unitId"),
//        region.unit.name.`as`("unitName")
//      )
//    )
//    .from(region)
//    .innerJoin(region.unit)
//    .where(this.eqUnitId(searchRegionDto.unitId))
//    .fetch()


  override fun getAllCities(): List<CityDto> = this.cityRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).map { CityDto(it.id, it.name) }

  override fun saveCity(entity: City): City = this.cityRepository.save(entity)

  override fun deleteCityById(id: Long) = this.cityRepository.deleteById(id)


  override fun getCityById(id: Long): City = this.cityRepository.findById(id).orElseThrow()

  override fun findCityById(id: Long): City? = this.cityRepository.findById(id).get()

  override fun findCityByName(name: String): City? = this.cityRepository.findByName(name)

  override fun existsCityByName(name: String): Boolean = this.cityRepository.existsByName(name)

  override fun getStreets(cityId: Long?): List<StreetDto> = this.queryFactory
    .select(
      Projections.constructor(
        StreetDto::class.java,
        street.id,
        street.name,
        street.city.id.`as`("cityId"),
        street.city.name.`as`("cityName")
      )
    )
    .from(street)
    .innerJoin(street.city)
    .where(this.eqCityId(cityId))
    .fetch()

  override fun saveStreet(entity: Street): Street = this.streetRepository.save(entity)

  override fun deleteStreetById(id: Long) = this.streetRepository.deleteById(id)


  override fun getStreetById(id: Long): Street = this.streetRepository.findById(id).orElseThrow()

  override fun findStreetById(id: Long): Street? = this.streetRepository.findById(id).get()

  override fun existsStreetByCityIdAndName(cityId: Long, name: String): Boolean = this.streetRepository.existsByCityIdAndName(cityId, name)

  private fun eqCityId(cityId: Long?): BooleanExpression? {
    if (cityId == null) return null
    return street.cityId.eq(cityId)
  }
}
