package com.herecity.user.application.port.input

interface CheckEmailDuplicateQuery {
    fun checkEmailDuplicate(query: In)
    data class In(val email: String)
}
