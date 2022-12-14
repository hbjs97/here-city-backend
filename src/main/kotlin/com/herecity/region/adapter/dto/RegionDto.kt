package com.herecity.region.adapter.dto

data class RegionDto(
  val id: Long?,
  val name: String
) {
  var upperRegion: RegionDto? = null

  constructor(id: Long, name: String, upperRegion: RegionDto) : this(id, name) {
    this.upperRegion = upperRegion
  }

  constructor(id: Long, name: String, upperRegionId: Long, upperRegionName: String) : this(id, name, RegionDto(upperRegionId, upperRegionName))
}
