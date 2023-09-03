package com.herecity.activity.adapter.web.response

import com.herecity.activity.application.port.input.SearchActivityQuery

data class FetchActivitiesResponse(
    val content: SearchActivityQuery.Out,
)
