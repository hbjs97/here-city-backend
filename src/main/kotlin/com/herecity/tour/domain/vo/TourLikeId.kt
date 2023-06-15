package com.herecity.tour.domain.vo

import java.io.Serializable
import java.util.UUID
import javax.persistence.Embeddable

@Embeddable
data class TourLikeId(
    val tourId: Long,
    val userId: UUID,
) : Serializable
