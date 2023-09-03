package com.herecity.activity.application.port.input

interface CreateActivityCommand {
    fun createActivity(command: In): Out
    data class In(
        val name: String,
    )

    data class Out(
        val id: Long,
        val name: String,
    )
}
