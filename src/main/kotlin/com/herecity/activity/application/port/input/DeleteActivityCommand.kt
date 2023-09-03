package com.herecity.activity.application.port.input

interface DeleteActivityCommand {
    fun deleteActivity(command: In)
    data class In(
        val id: Long,
    )
}
