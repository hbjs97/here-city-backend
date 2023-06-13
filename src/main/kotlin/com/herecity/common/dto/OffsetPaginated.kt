package com.herecity.common.dto

data class OffsetPaginated<T>(
    val content: List<T>,
    val meta: OffsetPageMeta,
)
