package com.herecity.activity.adapter.web.request

import com.herecity.activity.application.port.input.CreateActivityCommand
import javax.validation.constraints.Size

data class CreateActivityRequest(
    @field:Size(max = 20)
    val name: String,
) {
    fun toDomain(): CreateActivityCommand.In = CreateActivityCommand.In(
        name = name,
    )
}
