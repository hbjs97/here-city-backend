package com.herecity.place.domain.vo

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class PlaceLikeId(
  var place: Long? = null,

  var user: Long? = null,
) : Serializable
