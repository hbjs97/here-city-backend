package com.herecity.place.application.port.input.place

interface DeletePlaceCommand {
    fun deletePlace(command: In)
    data class In(
        val id: Long,
    )
}
