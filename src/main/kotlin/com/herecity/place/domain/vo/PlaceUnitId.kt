package com.herecity.place.domain.vo

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class PlaceUnitId(
//  @Column
  var place: Long? = null,

//  @Column
  var unit: Long? = null,
) : Serializable
