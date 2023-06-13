package com.herecity.common.dto

import kotlin.math.max

data class OffsetPageMeta(
    val total: Long,
    val page: Long,
    val limit: Long,
) {
    val maxPage: Long
        get() = max(((total + limit - 1) / limit) - 1, 0)
}
