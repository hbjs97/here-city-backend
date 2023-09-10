package com.herecity.place.domain.vo

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class PlaceUnitId(
    val place: Long,
    val unit: Long,
) : Serializable
