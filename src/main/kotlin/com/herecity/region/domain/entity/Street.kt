package com.herecity.region.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE street SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "street")
class Street(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(name = "city_id", insertable = false, updatable = false, nullable = false)
  var cityId: Long,

  @Column(nullable = false, length = 20)
  var name: String,

  @ManyToOne(targetEntity = City::class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "city_id")
  var city: City? = null
) : BaseEntity()
