package com.herecity.place.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.place.domain.vo.PlaceLikeId
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@SQLDelete(sql = "UPDATE place_like SET deleted_at = NOW() WHERE place_id = ? AND user_id = ?")
@IdClass(PlaceLikeId::class)
@Entity(name = "place_like")
class PlaceLike(
    @Id
    @Column
    var placeId: Long,

    @Id
    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Type(type = "uuid-char")
    var userId: UUID,
) : BaseEntity() {
    fun like() {
        this.deletedAt = null
    }

    fun disLike() {
        this.deletedAt = LocalDateTime.now()
    }

    fun liked(): Boolean = this.deletedAt == null
}
