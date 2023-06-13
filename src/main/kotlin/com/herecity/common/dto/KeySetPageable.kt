package com.herecity.common.dto

import com.herecity.common.domain.vo.Sort
import javax.validation.constraints.Size

data class KeySetPageable(
    @field:Size(min = 1, max = 30)
    val limit: Long = 20,

    val cursor: Long? = null,

    val order: Sort = Sort.DESC,
)
