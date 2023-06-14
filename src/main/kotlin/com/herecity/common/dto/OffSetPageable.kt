package com.herecity.common.dto

import com.herecity.common.domain.vo.Sort
import javax.validation.constraints.Max

data class OffSetPageable(
    val page: Long = 0,
    @field:Max(value = 30)
    val limit: Long = 20,
    val order: Sort = Sort.DESC,
) {
    fun offset(): Long = page * limit
}
