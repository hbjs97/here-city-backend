package com.herecity.activity.application.port.inbound

interface DeleteActivityCommand {
    fun deleteActivity(command: In)
    data class In(
        val id: Long,
    )
}
