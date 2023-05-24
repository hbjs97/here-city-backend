package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.tour.domain.vo.TouristId
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Type
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour_place SET deleted_at = NOW() WHERE tour_id = ? AND user_id = ?")
@IdClass(TouristId::class)
@Entity(name = "tourist")
class Tourist(
    @Id
    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Type(type = "uuid-char")
    var userId: UUID? = null,

    @Id
    @ManyToOne
    @JoinColumn(
        name = "tour_id",
        insertable = false,
        updatable = false,
        foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    var tour: Tour? = null,
) : BaseEntity()
