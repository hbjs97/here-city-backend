package com.herecity.tour.domain.vo

import java.io.Serializable
import java.util.*

data class TouristId(
  var tour: Long? = null,

  var user: UUID? = null,
) : Serializable
