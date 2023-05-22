package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.tour.application.dto.CreateTourPlaceDto
import com.herecity.tour.domain.vo.Budget
import com.herecity.tour.domain.vo.TourPlaceId
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour_place SET deleted_at = NOW() WHERE tour_id = ? AND place_id = ?")
@IdClass(TourPlaceId::class)
@Entity(name = "tour_place")
class TourPlace(
    @Id
    @ManyToOne
    @JoinColumn(
        name = "tour_id",
        insertable = false,
        updatable = false,
        foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    var tour: Tour? = null,

    @Id
    var placeId: Long = 0L,

    @Column(name = "`from`")
    var from: LocalDateTime,

    @Column(name = "`to`")
    var to: LocalDateTime,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tour_place_budget")
    @AttributeOverrides(
        value = [
            AttributeOverride(name = "type", column = Column(name = "type")),
            AttributeOverride(name = "amount", column = Column(name = "amount")),
            AttributeOverride(name = "description", column = Column(name = "description"))
        ]
    )

    var budgets: List<Budget>,
) : BaseEntity() {
    constructor(createTourPlaceDto: CreateTourPlaceDto, tour: Tour) : this(
        tour = tour,
        placeId = createTourPlaceDto.placeId,
        from = createTourPlaceDto.from,
        to = createTourPlaceDto.to,
        budgets = createTourPlaceDto.budgets,
    )
}
