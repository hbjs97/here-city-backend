package com.herecity.activity.adapter.inbound.web.response

import com.herecity.activity.application.port.input.SearchActivityQuery

data class FetchActivitiesResponse(
    val content: SearchActivityQuery.Out,
)
