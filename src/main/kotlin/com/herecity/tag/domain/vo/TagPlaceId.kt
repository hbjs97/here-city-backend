package com.herecity.tag.domain.vo

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class TagPlaceId(
  var tag: Long? = null,

  var place: Long? = null,
) : Serializable
