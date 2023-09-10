package com.herecity.activity.application.port.service

import com.herecity.activity.application.port.inbound.SearchActivityQuery
import com.herecity.activity.application.port.outbound.ActivityQueryOutputPort
import org.springframework.stereotype.Service

@Service
class ActivityQueryService(
    private val activityQueryOutputPort: ActivityQueryOutputPort,
) : SearchActivityQuery {
    override fun search(query: SearchActivityQuery.In): SearchActivityQuery.Out {
        val activities = this.activityQueryOutputPort.search(query.name)
        return SearchActivityQuery.Out(
            activities.map { SearchActivityQuery.Activity(id = it.id, name = it.name) },
        )
    }
}
