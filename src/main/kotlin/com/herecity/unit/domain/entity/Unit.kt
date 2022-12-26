package com.herecity.unit.domain.entity


import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE unit SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "unit")
class Unit(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(nullable = false, length = 20)
  var name: String,

  ) : BaseEntity() {
  @Column(nullable = false, length = 20)
  var isComposite: Boolean = false
}
