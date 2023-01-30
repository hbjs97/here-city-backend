package com.herecity.place.domain.vo

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class PlaceUnitId(
  var place: Long? = null,

  var unit: Long? = null,
) : Serializable
