package com.herecity.user.application.service

import com.herecity.s3.S3ClientAdapter
import com.herecity.s3.core.UploadObject
import com.herecity.user.application.port.inbound.UpdateUserProfileCommand
import com.herecity.user.application.port.outbound.UserCommandOutputPort
import com.herecity.user.application.port.outbound.UserQueryOutputPort
import dev.wimcorp.common.util.FileUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class UserCommandService(
    private val userQueryOutputPort: UserQueryOutputPort,
    private val userCommandOutputPort: UserCommandOutputPort,
    private val s3ClientAdapter: S3ClientAdapter,
) : UpdateUserProfileCommand {
    override fun updateProfile(command: UpdateUserProfileCommand.In): UpdateUserProfileCommand.Out {
        val user = userQueryOutputPort.getById(command.userId)
        command.displayName?.let {
            user.displayName = it
        }
        command.thumbnail?.let {
            user.thumbnail = s3ClientAdapter.upload(
                UploadObject(
                    objectKey = "${
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                    }-${UUID.randomUUID()}.${FileUtils.extractExtension(it.originalFilename)}",
                    file = it,
                )
            ).url
        }
        userCommandOutputPort.save(user)
        return UpdateUserProfileCommand.Out(
            id = user.id,
            displayName = user.displayName,
            thumbnail = user.thumbnail
        )
    }
}
