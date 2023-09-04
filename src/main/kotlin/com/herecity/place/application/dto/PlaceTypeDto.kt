package com.herecity.place.application.dto

import javax.validation.constraints.Size

data class PlaceTypeDto(
    val id: Long?,

    @Size(min = 2, max = 50)
    val name: String,
)
