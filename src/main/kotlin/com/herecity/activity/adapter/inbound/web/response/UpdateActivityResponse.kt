package com.herecity.activity.adapter.inbound.web.response

import com.herecity.activity.application.port.input.UpdateActivityCommand

data class UpdateActivityResponse(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(payload: UpdateActivityCommand.Out): UpdateActivityResponse =
            UpdateActivityResponse(
                id = payload.id,
                name = payload.name,
            )
    }
}
