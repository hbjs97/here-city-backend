package com.herecity.place.domain.entity

import StringListConverter
import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.locationtech.jts.geom.Point
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE place SET deleted_at = NOW() WHERE id = ?")
@DynamicInsert
@Entity(name = "place")
class Place(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(name = "place_type_id")
  var placeTypeId: Long,

  @Column(nullable = false)
  var regionId: Long,

  @Column(nullable = false, length = 100)
  var title: String,

  @Column(nullable = false, length = 100)
  var name: String,

  @Column(nullable = false, length = 255)
  var address: String,

  @Column(nullable = false, length = 600)
  var description: String,

  @Column(nullable = false)
  var rating: Double,

  @Column(nullable = false)
  var point: Point,

  @Column(nullable = false, length = 8, columnDefinition = "char(8)")
  var visitDate: String,

  @Convert(converter = StringListConverter::class)
  @Column(nullable = false, length = 1000)
  var images: List<String> = listOf(),

  @OneToMany(mappedBy = "place", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
  var placeUnits: MutableSet<PlaceUnit> = mutableSetOf(),

  @OneToMany(mappedBy = "place", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
  var placeActivities: MutableSet<PlaceActivity> = mutableSetOf(),

  @ManyToOne(targetEntity = PlaceType::class)
  @JoinColumn(name = "place_type_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var placeType: PlaceType? = null
) : BaseEntity()
