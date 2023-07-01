package com.herecity.tour.adapter.input.request

import com.herecity.tour.application.dto.CreateTourPlaceDto
import com.herecity.tour.application.port.input.CreateTourCommand
import com.herecity.tour.domain.vo.Scope
import java.time.LocalDateTime
import java.util.UUID
import javax.validation.Valid
import javax.validation.constraints.Size

data class CreateTourRequest(
    @field:Size(max = 50)
    val name: String,
    val regionId: Long,
    val scope: Scope,
    val from: LocalDateTime,
    val to: LocalDateTime,
    @field:Size(min = 1)
    @field:Valid
    val tourPlaces: List<CreateTourPlaceDto>,
    val tourists: Set<UUID> = mutableSetOf(),
) {
    fun toDomain(createdBy: UUID) = CreateTourCommand.In(
        createdBy = createdBy,
        name = name,
        regionId = regionId,
        scope = scope,
        from = from,
        to = to,
        tourPlaces = tourPlaces,
        tourists = tourists.plus(createdBy),
    )
}
