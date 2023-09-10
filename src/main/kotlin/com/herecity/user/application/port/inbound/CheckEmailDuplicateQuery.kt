package com.herecity.user.application.port.inbound

interface CheckEmailDuplicateQuery {
    fun checkEmailDuplicate(query: In)
    data class In(val email: String)
}
