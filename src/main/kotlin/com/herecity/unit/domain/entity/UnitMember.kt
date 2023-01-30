package com.herecity.unit.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.member.domain.entity.Member
import com.herecity.unit.domain.vo.UnitMemberId
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE unit_member SET deleted_at = NOW() WHERE unit_id = ? AND member_id = ?")
@IdClass(UnitMemberId::class)
@Entity(name = "unit_member")
class UnitMember(
  @Id
  @ManyToOne
  @JoinColumn(name = "member_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var member: Member? = null,

  @Id
  @ManyToOne
  @JoinColumn(name = "unit_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var unit: Unit? = null,
) : BaseEntity()
