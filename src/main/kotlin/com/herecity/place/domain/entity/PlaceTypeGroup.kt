package com.herecity.place.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.place.domain.vo.PlaceTypeGroupId
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE place_type SET deleted_at = NOW() WHERE id = ?")
@IdClass(PlaceTypeGroupId::class)
@Entity(name = "place_type_group")
class PlaceTypeGroup(
  @Id
  @ManyToOne
  @JoinColumn(name = "place_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var place: Place? = null,

  @Id
  @ManyToOne
  @JoinColumn(name = "place_type_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var type: PlaceType? = null,
) : BaseEntity()
