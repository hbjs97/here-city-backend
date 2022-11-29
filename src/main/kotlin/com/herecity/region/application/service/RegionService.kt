package com.herecity.region.application.service

import com.herecity.region.adapter.dto.CityDto
import com.herecity.region.adapter.dto.StreetDto
import com.herecity.region.application.dto.UpdateStreetDto
import com.herecity.region.application.port.input.LoadRegionUseCase
import com.herecity.region.application.port.input.RecordRegionUseCase
import com.herecity.region.application.port.output.CityCommandOutputPort
import com.herecity.region.application.port.output.CityQueryOutputPort
import com.herecity.region.application.port.output.StreetCommandOutputPort
import com.herecity.region.application.port.output.StreetQueryOutputPort
import com.herecity.region.domain.entity.City
import com.herecity.region.domain.entity.Street
import com.herecity.region.domain.exception.DuplicateCityNameException
import com.herecity.region.domain.exception.DuplicateStreetNameException
import org.springframework.stereotype.Service

@Service
class RegionService(
  private val cityQueryOutputPort: CityQueryOutputPort,
  private val cityCommandOutputPort: CityCommandOutputPort,
  private val streetQueryOutputPort: StreetQueryOutputPort,
  private val streetCommandOutputPort: StreetCommandOutputPort
) :
  LoadRegionUseCase, RecordRegionUseCase {
  override fun getCities(): List<CityDto> = this.cityQueryOutputPort.getAllCities()

  override fun getStreets(cityId: Long?): List<StreetDto> = this.streetQueryOutputPort.getStreets(cityId)

  override fun createCity(name: String): CityDto {
    if (this.cityQueryOutputPort.existsCityByName(name)) throw DuplicateCityNameException()
    val city = this.cityCommandOutputPort.saveCity(City(name = name))
    return CityDto(city)
  }

  override fun updateCityName(id: Long, name: String): CityDto {
    if (this.cityQueryOutputPort.existsCityByName(name)) throw DuplicateCityNameException()
    val city = this.cityQueryOutputPort.getCityById(id)
    city.name = name
    this.cityCommandOutputPort.saveCity(city)
    return CityDto(city)
  }

  override fun deleteCity(id: Long) = this.cityCommandOutputPort.deleteCityById(id)

  override fun addStreet(cityId: Long, name: String): StreetDto {
    if (this.streetQueryOutputPort.existsStreetByCityIdAndName(cityId, name)) throw DuplicateStreetNameException()
    val city = this.cityQueryOutputPort.getCityById(cityId)
    val street = this.streetCommandOutputPort.saveStreet(Street(cityId = cityId, name = name))
    return StreetDto(street, city)
  }

  override fun updateStreet(id: Long, updateStreetDto: UpdateStreetDto): StreetDto {
    val street = this.streetQueryOutputPort.getStreetById(id)
    if (updateStreetDto.cityId != null) street.cityId = updateStreetDto.cityId

    val city = this.cityQueryOutputPort.getCityById(street.cityId)
    if (updateStreetDto.name != null) street.name = updateStreetDto.name
    if (this.streetQueryOutputPort.existsStreetByCityIdAndName(street.cityId, street.name)) throw DuplicateStreetNameException()
    this.streetCommandOutputPort.saveStreet(street)
    return StreetDto(street, city)
  }

  override fun deleteStreet(id: Long) = this.streetCommandOutputPort.deleteStreetById(id)

}
