package com.herecity.activity.application.port.service

import com.herecity.activity.application.port.input.SearchActivityQuery
import com.herecity.activity.application.port.output.ActivityQueryOutputPort
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
