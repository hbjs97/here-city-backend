package com.herecity.common.domain.entity

import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@Where(clause = "deleted_at IS NULL")
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
  @CreatedDate
  lateinit var createdAt: LocalDateTime

  var deletedAt: LocalDateTime? = null
}
