package com.herecity.place.domain.entity

import com.herecity.activity.domain.entity.Activity
import com.herecity.common.domain.entity.BaseEntity
import com.herecity.place.domain.vo.PlaceActivityId
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE place_activity SET deleted_at = NOW() WHERE place_id = ? AND activity_id = ?")
@IdClass(PlaceActivityId::class)
@Entity(name = "place_activity")
class PlaceActivity(
  @Id
  @ManyToOne
  @JoinColumn(name = "place_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var place: Place? = null,

  @Id
  @ManyToOne
  @JoinColumn(name = "activity_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var activity: Activity? = null
) : BaseEntity()
