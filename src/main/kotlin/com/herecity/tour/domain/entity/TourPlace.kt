package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.place.domain.entity.Place
import com.herecity.tour.domain.vo.TourPlaceId
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour_place SET deleted_at = NOW() WHERE tour_id = ? AND place_id = ?")
@IdClass(TourPlaceId::class)
@Entity(name = "tour_place")
class TourPlace(
  @Id
  @ManyToOne
  @JoinColumn(name = "tour_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var tour: Tour? = null,

  @Id
  @ManyToOne
  @JoinColumn(name = "place_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var place: Place? = null,

  @Column(nullable = false)
  var from: LocalDateTime,

  @Column(nullable = false)
  var to: LocalDateTime,

  @Column(nullable = false)
  var budget: Int,

  @Column(nullable = false)
  var description: String
) : BaseEntity()
