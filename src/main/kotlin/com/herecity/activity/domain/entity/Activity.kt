package com.herecity.member.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE activity SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "activity")
class Activity (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,
  
  @Column(nullable = false, length = 20)
  var name: String
): BaseEntity()
