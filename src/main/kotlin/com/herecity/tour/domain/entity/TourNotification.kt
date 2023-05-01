package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour_notification SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "tour_notification")
class TourNotification(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,

  @ManyToOne
  @JoinColumn(
    name = "tour_id",
    insertable = true,
    updatable = true,
    foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
  )
  var tour: Tour? = null,

  @Column
  var scheduledAt: LocalDateTime,

  @Column
  var sendedAt: LocalDateTime? = null,
) : BaseEntity()
