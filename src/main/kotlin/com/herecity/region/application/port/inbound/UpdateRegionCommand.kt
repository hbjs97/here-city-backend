package com.herecity.region.application.port.inbound

interface UpdateRegionCommand {
    fun updateRegion(command: In): Out
    data class In(
        val id: Long,
        val name: String?,
    )

    data class Out(
        val id: Long,
        val name: String,
    )
}
