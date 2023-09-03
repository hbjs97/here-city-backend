package com.herecity.activity.application.port.input

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
