package com.herecity.activity.application.port.inbound

interface UpdateActivityCommand {
    fun updateActivity(command: In): Out
    data class In(
        val id: Long,
        val name: String,
    )

    data class Out(
        val id: Long,
        val name: String,
    )
}
