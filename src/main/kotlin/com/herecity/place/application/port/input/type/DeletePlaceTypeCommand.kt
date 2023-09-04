package com.herecity.place.application.port.input.type

interface DeletePlaceTypeCommand {
    fun deletePlaceType(command: In)
    data class In(
        val id: Long,
    )
}
