package com.herecity.unit.application.port.inbound

interface CreateUnitCommand {
    fun createUnit(command: In): Out

    data class In(
        val name: String,
    )

    data class Out(
        val id: Long,
        val name: String,
    )
}
