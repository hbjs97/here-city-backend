package com.herecity.activity.adapter.web.response

import com.herecity.activity.application.port.input.CreateActivityCommand

data class CreateActivityResponse(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(payload: CreateActivityCommand.Out): CreateActivityResponse =
            CreateActivityResponse(
                id = payload.id,
                name = payload.name,
            )
    }
}
