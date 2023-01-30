package com.herecity.unit.domain.vo

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class UnitMemberId(
  var unit: Long? = null,

  var member: Long? = null,
) : Serializable
