package com.herecity.tour.domain.entity

import com.herecity.common.domain.entity.BaseEntity
import com.herecity.tour.domain.vo.Scope
import org.hibernate.annotations.SQLDelete
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
  var id: Long? = null,

  @Column(length = 50, nullable = false)
  var name: String,

  @Column(length = 30, nullable = false)
  var regionId: Long,

  @Enumerated(EnumType.STRING)
  var scope: Scope,

  @Column(nullable = false, name = "`from`")
  var from: LocalDateTime,

  @Column(nullable = false, name = "`to`")
  var to: LocalDateTime,

  @Column(nullable = false)
  var isDone: Boolean = false,

  @Column(nullable = false)
  var recommends: Int = 0,

  @Column(nullable = false)
  var favorites: Int = 0,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
  var tourPlaces: MutableSet<TourPlace> = mutableSetOf(),

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
  var tourists: MutableSet<Tourist> = mutableSetOf(),

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
  var tourNotifications: MutableList<TourNotification> = mutableListOf(),
) : BaseEntity()
