package com.herecity.place.domain.vo

import java.io.Serializable

data class PlaceActivityId(
    val place: Long,
    val activity: Long,
) : Serializable
