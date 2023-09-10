package com.herecity.unit.application.port.inbound

interface UpdateUnitCommand {
    fun updateUnit(command: In): Out
    data class In(
        val id: Long,
        val name: String,
    )

    data class Out(
        val id: Long,
        val name: String,
    )
}
