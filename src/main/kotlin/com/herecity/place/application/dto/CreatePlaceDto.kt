package com.herecity.place.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import javax.validation.constraints.Size
import org.springframework.data.geo.Point as Location

data class CreatePlaceDto(
  val unitIds: @Size(min = 1) List<Long>,
  val activityIds: @Size(min = 1) List<Long>,
  val placeTypeId: Long,
  val regionId: Long,
  val name: String,
  val address: String,
  val location: Location
) {
  @Schema(hidden = true)
  val point: Point = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(this.location.x, this.location.y))
}
