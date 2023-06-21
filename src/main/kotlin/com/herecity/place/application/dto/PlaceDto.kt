package com.herecity.place.application.dto

import com.herecity.place.domain.entity.Place
import org.locationtech.jts.geom.Point

data class PlaceDto(
    val id: Long,
    val title: String,
    val name: String,
    val description: String?,
    val address: String,
    val point: Coordinate2D,
    val rating: Double,
    val images: List<String> = emptyList(),
    var distance: Double? = null,
) {
    var liked: Boolean = false

    constructor(
        id: Long,
        title: String,
        name: String,
        description: String?,
        address: String,
        point: Point,
        rating: Double,
        images: List<String>,
    ) : this(
        id = id,
        title = title,
        name = name,
        description = description,
        address = address,
        point = Coordinate2D(point.x, point.y),
        rating = rating,
        images = images
    )

    constructor(place: Place) : this(
        id = place.id,
        title = place.title,
        name = place.name,
        description = place.description,
        address = place.address,
        point = Coordinate2D(place.point.x, place.point.y),
        rating = place.rating,
        images = place.images
    )
}
