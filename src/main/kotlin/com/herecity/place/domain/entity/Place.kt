package com.herecity.place.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.geo.Point
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE place SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "place")
class Place(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(name = "place_type_id", insertable = false, updatable = false)
  var placeTypeId: Long,

  @Column(name = "region_id", insertable = false, updatable = false)
  var regionId: Long,

  @Column(nullable = false, length = 100)
  var name: String,

  @Column(nullable = false, length = 255)
  var address: String,

  @Column(nullable = false)
  var point: Point
) : BaseEntity()
