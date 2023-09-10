package com.herecity.activity.application.port.service

import com.herecity.activity.application.port.inbound.CreateActivityCommand
import com.herecity.activity.application.port.inbound.DeleteActivityCommand
import com.herecity.activity.application.port.inbound.UpdateActivityCommand
import com.herecity.activity.application.port.outbound.ActivityCommandOutputPort
import com.herecity.activity.application.port.outbound.ActivityQueryOutputPort
import com.herecity.activity.domain.entity.Activity
import com.herecity.activity.domain.exception.DuplicateActivityNameException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ActivityCommandService(
    private val activityQueryOutputPort: ActivityQueryOutputPort,
    private val activityCommandOutputPort: ActivityCommandOutputPort,
) : CreateActivityCommand, UpdateActivityCommand, DeleteActivityCommand {
    @Transactional
    override fun createActivity(command: CreateActivityCommand.In): CreateActivityCommand.Out {
        if (this.activityQueryOutputPort.findByName(command.name) != null) {
            throw DuplicateActivityNameException()
        }
        this.activityCommandOutputPort.save(Activity(name = command.name)).let {
            return CreateActivityCommand.Out(
                id = it.id,
                name = it.name,
            )
        }
    }

    @Transactional
    override fun updateActivity(command: UpdateActivityCommand.In): UpdateActivityCommand.Out {
        val activity = this.activityQueryOutputPort.getById(command.id)
        activity.name = command.name
        this.activityCommandOutputPort.save(activity).let {
            return UpdateActivityCommand.Out(
                id = it.id,
                name = it.name,
            )
        }
    }

    @Transactional
    override fun deleteActivity(command: DeleteActivityCommand.In) {
        val activity = this.activityQueryOutputPort.getById(command.id).apply { delete() }
        this.activityCommandOutputPort.save(activity)
    }

}
