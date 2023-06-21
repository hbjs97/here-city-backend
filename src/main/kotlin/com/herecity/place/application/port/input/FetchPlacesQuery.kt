package com.herecity.place.application.port.input

import com.herecity.place.application.dto.PlaceDto

interface FetchPlacesQuery {
    fun fetchPlaces(query: In): Out
    data class In(val ids: List<Long>)
    data class Out(val places: List<PlaceDto>)
}
