package com.herecity.common.domain.entity

import java.time.LocalDateTime

open class BaseDomain {
  var createdAt: LocalDateTime
  var deletedAt: LocalDateTime?

  constructor(entity: BaseEntity) {
    this.createdAt = entity.createdAt
    this.deletedAt = entity.deletedAt
  }

  fun softDelete() {
    this.deletedAt = LocalDateTime.now()
  }
}
