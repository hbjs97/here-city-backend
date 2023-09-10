package com.herecity.activity.application.port.inbound

interface SearchActivityQuery {
    fun search(query: In): Out
    data class In(
        val name: String?,
    )

    data class Out(
        val activities: List<Activity>,
    )

    data class Activity(
        val id: Long,
        val name: String,
    )
}
