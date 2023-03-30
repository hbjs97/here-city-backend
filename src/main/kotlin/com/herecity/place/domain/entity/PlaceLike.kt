package com.herecity.place.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.place.domain.vo.PlaceLikeId
import com.herecity.user.domain.entity.User
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE place_like SET deleted_at = NOW() WHERE place_id = ? AND user_id = ?")
@IdClass(PlaceLikeId::class)
@Entity(name = "place_like")
class PlaceLike(
  @Id
  @ManyToOne
  @JoinColumn(name = "place_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var place: Place? = null,

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var user: User? = null,
) : BaseEntity()
