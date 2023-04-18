package com.herecity.place.application.dto

import com.herecity.place.domain.entity.Place
import org.locationtech.jts.geom.Point

data class PlaceDto(
  val id: Long?,
  val title: String,
  val name: String,
  val description: String?,
  val address: String,
  val point: Point,
  val rating: Double,
//  val liked: Boolean = false,
  val images: List<String> = emptyList(),
//  val distance: String?, // (m)
) {
  constructor(place: Place) : this(
    id = place.id,
    title = place.title,
    name = place.name,
    description = place.description,
    address = place.address,
    point = place.point,
    rating = place.rating,
//    distance = null,
    images = place.images
  )
}
