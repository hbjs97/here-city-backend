package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Type
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour_review SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "tour_review")
class TourReview(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,

  @Column
  var tourId: Long,

  @Column
  var placeReviewId: Long,

  @GenericGenerator(name = "uuid4", strategy = "uuid4")
  @Type(type = "uuid-char")
  var createdBy: UUID,

  @Column(nullable = true, length = 500)
  var drawingDiary: String? = null,
) : BaseEntity()
