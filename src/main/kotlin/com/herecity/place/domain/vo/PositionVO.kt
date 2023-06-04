package com.herecity.place.domain.vo

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Point

data class PositionVO(
    val x: Double, // longitude
    val y: Double, // latitude
) {
    constructor(x: String, y: String) : this(x.toDouble(), y.toDouble())
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
    constructor(x: Long, y: Long) : this(x.toDouble(), y.toDouble())
    constructor(x: Float, y: Float) : this(x.toDouble(), y.toDouble())
    constructor(coordinate: Coordinate) : this(coordinate.x, coordinate.y)
    constructor(point: Point) : this(point.x, point.y)
}
