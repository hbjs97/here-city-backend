package com.herecity.tour.adapter.input.request

import com.herecity.tour.application.port.input.UpdateTourCommand
import com.herecity.tour.domain.vo.Scope
import java.time.LocalDateTime
import javax.validation.constraints.Size

data class UpdateTourRequest(
    @field:Size(max = 50)
    val name: String? = null,
    val scope: Scope? = null,
    val from: LocalDateTime? = null,
    val to: LocalDateTime? = null,
) {
    fun toDomain(id: Long) = UpdateTourCommand.In(
        id = id,
        name = name,
        scope = scope,
        from = from,
        to = to,
    )
}
