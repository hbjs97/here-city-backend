package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.tour.application.dto.CreateTourPlaceDto
import com.herecity.tour.domain.vo.Scope
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Type
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tour SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "tour")
class Tour(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(length = 50)
    var name: String,

    @Column(length = 30)
    var regionId: Long,

    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Type(type = "uuid-char")
    var createdBy: UUID,

    @Enumerated(EnumType.STRING)
    var scope: Scope,

    @Column(name = "`from`")
    var from: LocalDateTime,

    @Column(name = "`to`")
    var to: LocalDateTime,

    @Column
    val rating: Double = 0.0,

    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Type(type = "uuid-char")
    val joinCode: UUID = UUID.randomUUID(),

    @Column
    var recommends: Int = 0,

    @Column
    var favorites: Int = 0,

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "tour",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    var tourPlaces: Set<TourPlace> = mutableSetOf(),

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "tour",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    var tourists: Set<Tourist> = mutableSetOf(),
) : BaseEntity() {
    fun addTourPlace(createTourPlaceDto: CreateTourPlaceDto) {
        this.tourPlaces = tourPlaces.plus(TourPlace(createTourPlaceDto, this))
    }

    fun addTourist(userId: UUID) {
        this.tourists = tourists.plus(Tourist(userId, this))
    }

    fun isHost(id: UUID): Boolean = this.createdBy == id
}
