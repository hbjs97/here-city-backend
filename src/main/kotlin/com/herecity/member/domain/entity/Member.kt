package com.herecity.member.domain.entity


import com.herecity.common.domain.entity.BaseEntity
import com.herecity.unit.domain.entity.Unit
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "member")
class Member(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(name = "unit_id", insertable = false, updatable = false)
  var unitId: Long? = null,

  @Column(nullable = false)
  var name: String,

  @ManyToOne(targetEntity = Unit::class, fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var unit: Unit? = null
) : BaseEntity()
