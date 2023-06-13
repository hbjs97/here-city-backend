package com.herecity.region.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE region SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(
    name = "region",
    indexes = [
        Index(columnList = "name, upper_region_id, deletedAt")
    ]
)
class Region(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 20)
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_region_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var upperRegion: Region? = null
) : BaseEntity()
