package com.herecity.region.application.dto

import javax.validation.constraints.Size

data class RegionDto(
    val id: Long,

    @field:Size(min = 2, max = 20)
    val name: String,
)
