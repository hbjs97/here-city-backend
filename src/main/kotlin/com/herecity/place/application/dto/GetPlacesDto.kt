package com.herecity.place.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel

data class GetPlacesDto(
  val placeTypeId: Long?,

  val activityId: Long?,

  val unitId: Long?,

  val name: String?,

  val x: Double?,

  val y: Double?,
) {
  @Schema(hidden = true)
  val point: Point = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(this.x!!, this.y!!))
  fun coordinates(): Coordinate = Coordinate(this.x!!, this.y!!)
}
