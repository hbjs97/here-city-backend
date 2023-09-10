package com.herecity.activity.adapter.inbound.web.response

import com.herecity.activity.application.port.inbound.CreateActivityCommand

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
