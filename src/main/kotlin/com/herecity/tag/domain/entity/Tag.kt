package com.herecity.tag.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tag SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "tag")
class Tag(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(nullable = false, length = 100)
  var name: String,
) : BaseEntity()
