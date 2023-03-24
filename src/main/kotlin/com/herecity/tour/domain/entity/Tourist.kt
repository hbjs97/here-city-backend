package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.tour.domain.vo.TouristId
import com.herecity.user.domain.entity.User
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour_place SET deleted_at = NOW() WHERE tour_id = ? AND user_id = ?")
@IdClass(TouristId::class)
@Entity(name = "tourist")
class Tourist(
  @Id
  @ManyToOne
  @JoinColumn(name = "tour_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var tour: Tour? = null,

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var user: User? = null
) : BaseEntity()
