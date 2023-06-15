package com.herecity.tour.application.port.input

interface ShareTourQuery {
    fun shareJoinCode(query: In): Out
    data class In(val id: Long)
    data class Out(val joinCode: String)
}
