package com.herecity.place.application.port.input.place

import org.locationtech.jts.geom.Coordinate

interface FetchPlaceQuery {
    fun fetchPlace(query: In): Out
    data class In(val id: Long)
    data class Out(
        val id: Long,
        val title: String,
        val name: String,
        val description: String?,
        val address: String,
        val point: Coordinate,
        val rating: Double,
        val images: List<String>,
        var distance: Double?,
    )
}
