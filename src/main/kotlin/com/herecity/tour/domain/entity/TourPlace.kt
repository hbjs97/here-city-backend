package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.tour.application.dto.CreateTourPlaceDto
import com.herecity.tour.domain.vo.TourPlaceId
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour_place SET deleted_at = NOW() WHERE tour_id = ? AND place_id = ?")
@IdClass(TourPlaceId::class)
@Entity(name = "tour_place")
class TourPlace(
  @Id
  @ManyToOne
  @JoinColumn(
    name = "tour_id",
    insertable = false,
    updatable = false,
    foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
  )
  var tour: Tour? = null,

  @Id
  var placeId: Long = 0L,

  @Column(name = "`from`")
  var from: LocalDateTime,

  @Column(name = "`to`")
  var to: LocalDateTime,

  @Column
  var budget: Int,

  @Column
  var description: String
) : BaseEntity() {
  constructor(createTourPlaceDto: CreateTourPlaceDto, tour: Tour) : this(
    tour = tour,
    placeId = createTourPlaceDto.placeId,
    from = createTourPlaceDto.from,
    to = createTourPlaceDto.to,
    budget = createTourPlaceDto.budget,
    description = createTourPlaceDto.description
  )
}
