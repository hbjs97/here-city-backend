package com.herecity.place.domain.vo

import java.io.Serializable

data class PlaceTypeGroupId(
    val place: Long,
    val type: Long,
) : Serializable
