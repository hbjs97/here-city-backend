package com.herecity.place.domain.vo

import java.io.Serializable

data class PlaceActivityId(
    var place: Long? = null,
    var activity: Long? = null,
) : Serializable
