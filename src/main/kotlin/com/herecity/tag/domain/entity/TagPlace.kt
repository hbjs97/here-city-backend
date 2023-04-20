package com.herecity.tag.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.place.domain.entity.Place
import com.herecity.tag.domain.vo.TagPlaceId
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tag_place SET deleted_at = NOW() WHERE place_id = ? AND user_id = ?")
@IdClass(TagPlaceId::class)
@Entity(name = "tag_place")
class TagPlace(
  @Id
  @ManyToOne
  @JoinColumn(name = "tag_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var tag: Tag? = null,

  @Id
  @ManyToOne
  @JoinColumn(name = "place_id", insertable = false, updatable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  var place: Place? = null,
) : BaseEntity()
