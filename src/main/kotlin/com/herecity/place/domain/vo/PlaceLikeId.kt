package com.herecity.place.domain.vo

import java.io.Serializable
import java.util.UUID
import javax.persistence.Embeddable

@Embeddable
data class PlaceLikeId(
    val placeId: Long,
    val userId: UUID,
) : Serializable
