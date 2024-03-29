package com.herecity.place.domain.entity

import StringListConverter
import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.Comment
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Type
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE place_review SET deleted_at = NOW() WHERE id = ?")
@Entity(name = "place_review")
class PlaceReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column
    var placeId: Long,

    @Comment("특정 투어와 관련된 리뷰인 경우 해당 투어의 id를 저장")
    @Column
    var tourId: Long? = null,

    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Type(type = "uuid-char")
    var createdBy: UUID,

    @Column
    val rating: Double,

    @Column(length = 50)
    val content: String,

    @Convert(converter = StringListConverter::class)
    @Column(nullable = false, length = 2000)
    var images: List<String> = listOf(),
) : BaseEntity()
