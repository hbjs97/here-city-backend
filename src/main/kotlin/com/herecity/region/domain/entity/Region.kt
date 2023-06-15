package com.herecity.region.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

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
    var id: Long = 0L,

    @Column(nullable = false, length = 20)
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_region_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var upperRegion: Region? = null,
) : BaseEntity()
