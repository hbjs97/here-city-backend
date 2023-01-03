package com.herecity.place.domain.entity

import com.herecity.place.domain.vo.PlaceUnitId
import com.herecity.unit.domain.entity.Unit
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE place_unit SET deleted_at = NOW() WHERE place_id = ? AND unit_id = ?")
@IdClass(PlaceUnitId::class)
@Entity(name = "place_unit")
class PlaceUnit(
  @Id
  @ManyToOne
  @JoinColumn(name = "place_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var place: Place? = null,

  @Id
  @ManyToOne
  @JoinColumn(name = "unit_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var unit: Unit? = null,
)
