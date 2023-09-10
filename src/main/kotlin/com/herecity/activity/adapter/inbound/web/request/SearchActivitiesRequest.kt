package com.herecity.activity.adapter.inbound.web.request

import com.herecity.activity.application.port.inbound.SearchActivityQuery

data class SearchActivitiesRequest(
    val name: String?,
) {
    fun toDomain(): SearchActivityQuery.In = SearchActivityQuery.In(
        name = this.name,
    )
}
