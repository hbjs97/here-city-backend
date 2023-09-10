package com.herecity.region.application.port.inbound

interface CreateRegionCommand {
    fun createRegion(command: In): Out
    data class In(
        val name: String,
    )

    data class Out(
        val id: Long,
        val name: String,
    )
}
