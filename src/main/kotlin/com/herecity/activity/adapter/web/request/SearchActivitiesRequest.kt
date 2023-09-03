package com.herecity.activity.adapter.web.request

import com.herecity.activity.application.port.input.SearchActivityQuery

data class SearchActivitiesRequest(
    val name: String?,
) {
    fun toDomain(): SearchActivityQuery.In = SearchActivityQuery.In(
        name = this.name,
    )
}
