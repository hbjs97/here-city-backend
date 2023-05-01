package com.herecity.tour.domain.vo

import java.io.Serializable

data class TourPlaceId(
  var tour: Long? = null,
  var placeId: Long = 0L,
) : Serializable
