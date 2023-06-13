package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.tour.application.dto.CreateTourDto
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
    var tourPlaces: Set<TourPlace> = setOf(),

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "tour",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    var tourists: Set<Tourist> = setOf(),

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "tour",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        orphanRemoval = true
    )
    var tourNotifications: List<TourNotification> = listOf(),
) : BaseEntity() {
    constructor(createTourDto: CreateTourDto, createdBy: UUID) : this(
        name = createTourDto.name,
        regionId = createTourDto.regionId,
        createdBy = createdBy,
        scope = createTourDto.scope,
        from = createTourDto.from,
        to = createTourDto.to,
    ) {
        this.tourPlaces = createTourDto.tourPlaces.map { TourPlace(it, this) }.toSet()
        this.tourists = createTourDto.tourists.plus(createdBy).map { Tourist(it, this) }.toSet()
        this.tourNotifications =
            createTourDto.notifications.map { TourNotification(scheduledAt = it.scheduledAt, tour = this) }.toList()
    }

    fun isHost(id: UUID): Boolean = this.createdBy == id
}
