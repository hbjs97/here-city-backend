package com.herecity.place.application.dto

import com.herecity.place.domain.entity.Place
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Point

data class PlaceDto(
  val id: Long?,
  val title: String,
  val name: String,
  val description: String?,
  val address: String,
  val point: Coordinate,
  val rating: Double,
  val images: List<String> = emptyList(),
) {
  constructor(
    id: Long,
    title: String,
    name: String,
    description: String?,
    address: String,
    point: Point,
    rating: Double,
    images: List<String>
  ) : this(
    id = id,
    title = title,
    name = name,
    description = description,
    address = address,
    point = point.coordinate,
    rating = rating,
    images = images
  )
  
  constructor(place: Place) : this(
    id = place.id,
    title = place.title,
    name = place.name,
    description = place.description,
    address = place.address,
    point = place.point.coordinate,
    rating = place.rating,
    images = place.images
  )
}
