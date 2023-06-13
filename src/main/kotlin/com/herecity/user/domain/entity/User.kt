package com.herecity.user.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.user.domain.vo.ProviderType
import com.herecity.user.domain.vo.UserRole
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Type
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(
    name = "user",
    indexes = [
        Index(columnList = "providerId, deletedAt"),
        Index(columnList = "fcmToken, deletedAt")
    ]
)
class User(
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Type(type = "uuid-char")
    var id: UUID = UUID.randomUUID(),

    @Column(length = 100, unique = true)
    var providerId: String? = null,

    @Column(length = 100, unique = true)
    var email: String,

    @Column(length = 255)
    var password: String,

    @Column(length = 30, nullable = false)
    var displayName: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

    @Enumerated(EnumType.STRING)
    var provider: ProviderType? = null,

    @Column
    var thumbnail: String? = null,

    @Column(length = 200, nullable = true)
    var fcmToken: String? = null,
) : BaseEntity()
