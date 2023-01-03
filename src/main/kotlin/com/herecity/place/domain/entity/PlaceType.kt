package com.herecity.place.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE place_type SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "place_type")
class PlaceType(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(nullable = false, length = 50)
  var name: String,
) : BaseEntity()
