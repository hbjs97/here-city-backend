package com.herecity.activity.adapter.inbound.web.request

import com.herecity.activity.application.port.input.UpdateActivityCommand
import javax.validation.constraints.Size

data class UpdateActivityRequest(
    @field:Size(max = 20)
    val name: String,
) {
    fun toDomain(id: Long): UpdateActivityCommand.In = UpdateActivityCommand.In(
        id = id,
        name = name,
    )
}
