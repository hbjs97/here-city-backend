package com.herecity.place.application.port.input.type

interface CreatePlaceTypeCommand {
    fun createPlaceType(command: In): Out
    data class In(
        val name: String,
    )

    data class Out(
        val id: Long,
        val name: String,
    )
}
