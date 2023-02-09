package com.herecity.tour.domain.vo

import com.herecity.place.domain.entity.Place
import java.io.Serializable


data class TourPlaceId(
  var tour: Long? = null,

  var place: Place? = null,
) : Serializable
