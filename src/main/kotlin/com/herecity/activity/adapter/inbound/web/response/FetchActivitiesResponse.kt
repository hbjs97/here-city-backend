package com.herecity.activity.adapter.inbound.web.response

import com.herecity.activity.application.port.inbound.SearchActivityQuery

data class FetchActivitiesResponse(
    val content: SearchActivityQuery.Out,
)
