package com.herecity.region.adapter.dto

import com.herecity.region.domain.entity.Region
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Size


data class RegionDto(
  val id: Long,

  @Size(min = 2, max = 20)
  val name: String,
) {
  @Schema(implementation = RegionDto::class, name = "upperRegion")
  var upperRegion: RegionDto? = null

  constructor(id: Long, name: String, upperRegion: RegionDto) : this(id, name) {
    this.upperRegion = upperRegion
  }

  constructor(id: Long, name: String, upperRegion: Region) : this(id, name) {
    this.upperRegion = RegionDto(id = upperRegion.id!!, name = upperRegion.name)
  }

  constructor(region: Region) : this(region.id!!, region.name) {
    if (region.upperRegion !== null) {
      this.upperRegion = RegionDto(id = region.upperRegion!!.id!!, name = region.upperRegion!!.name)
    }
  }

  constructor(id: Long, name: String, upperRegionId: Long, upperRegionName: String) : this(id, name, RegionDto(upperRegionId, upperRegionName))

}
