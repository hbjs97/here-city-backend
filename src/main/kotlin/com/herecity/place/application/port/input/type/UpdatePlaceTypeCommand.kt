package com.herecity.place.application.port.input.type

interface UpdatePlaceTypeCommand {
    fun updatePlaceType(command: In): Out
    data class In(
        val id: Long,
        val name: String,
    )

    data class Out(
        val id: Long,
        val name: String,
    )
}
