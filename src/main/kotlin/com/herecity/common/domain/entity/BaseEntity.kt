package com.herecity.common.domain.entity

import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@Where(clause = "deleted_at IS NULL")
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
  @CreatedDate
  @Column(nullable = false, updatable = false)
  var createdAt: LocalDateTime = LocalDateTime.now()

  @Column(nullable = true)
  var deletedAt: LocalDateTime? = null
}
