package com.herecity.unit.application.port.inbound

interface DeleteUnitCommand {
    fun deleteUnit(command: In)
    data class In(
        val id: Long,
    )
}
