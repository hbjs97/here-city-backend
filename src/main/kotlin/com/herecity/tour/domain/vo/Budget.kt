package com.herecity.tour.domain.vo

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Budget(
    @Column
    val type: String,
    @Column
    val amount: Int,
    @Column
    val description: String,
)
