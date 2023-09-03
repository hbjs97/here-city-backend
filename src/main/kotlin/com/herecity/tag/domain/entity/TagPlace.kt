package com.herecity.tag.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tag_place SET deleted_at = NOW() WHERE tag_id = ? AND place_id = ?")
@Entity(name = "tag_place")
class TagPlace(
    @Id
    var tagId: Long = 0L,

    @Id
    var placeId: Long = 0L,
) : BaseEntity(), Serializable
