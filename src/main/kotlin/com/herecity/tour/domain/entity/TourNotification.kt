package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour_notification SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "tour_notification")
class TourNotification(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(name = "tour_id", nullable = false)
  var tourId: Long,

  @Column(nullable = false)
  var userId: UUID,

  @Column(nullable = false)
  var scheduledAt: LocalDateTime,

  @Column(nullable = true)
  var sendedAt: LocalDateTime? = null,

  @ManyToOne
  @JoinColumn(name = "tour_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var tour: Tour? = null,
) : BaseEntity()
