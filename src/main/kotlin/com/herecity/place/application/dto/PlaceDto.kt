package com.herecity.place.application.dto

import com.herecity.place.domain.entity.Place
import org.locationtech.jts.geom.Point

data class PlaceDto(
  val id: Long?,

  val name: String,

  val desc: String?,

  val address: String,

  val point: Point,

  val rating: Double,
//
//  val liked: Boolean = false,
//
//  val distance: Int, // (m)
//
  val images: List<String> = emptyList(),
) {
  constructor(place: Place) : this(
    id = place.id,
    name = place.name,
    desc = place.description,
    address = place.address,
    point = place.point,
    rating = place.rating,
//    liked = place.liked,
//    distance = place.distance,
    images = place.images
  )
}
