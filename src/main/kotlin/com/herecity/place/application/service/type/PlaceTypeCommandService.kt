package com.herecity.place.application.service.type

import com.herecity.place.application.port.input.type.CreatePlaceTypeCommand
import com.herecity.place.application.port.input.type.DeletePlaceTypeCommand
import com.herecity.place.application.port.input.type.UpdatePlaceTypeCommand
import com.herecity.place.application.port.output.type.PlaceTypeCommandOutputPort
import com.herecity.place.application.port.output.type.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.PlaceType
import com.herecity.place.domain.exception.DuplicatePlaceTypeNameException
import org.springframework.stereotype.Service

@Service
class PlaceTypeCommandService(
    private val placeTypeQueryOutputPort: PlaceTypeQueryOutputPort,
    private val placeTypeCommandOutputPort: PlaceTypeCommandOutputPort,
) : CreatePlaceTypeCommand,
    DeletePlaceTypeCommand,
    UpdatePlaceTypeCommand {
    override fun createPlaceType(command: CreatePlaceTypeCommand.In): CreatePlaceTypeCommand.Out {
        val exist = placeTypeQueryOutputPort.existsByName(command.name)
        if (exist) throw DuplicatePlaceTypeNameException()

        placeTypeCommandOutputPort.save(PlaceType(name = command.name)).let {
            return CreatePlaceTypeCommand.Out(id = it.id, name = it.name)
        }
    }

    override fun updatePlaceType(command: UpdatePlaceTypeCommand.In): UpdatePlaceTypeCommand.Out {
        val placeType = placeTypeQueryOutputPort.getById(command.id)
        placeType.name = command.name
        placeTypeCommandOutputPort.save(placeType)
        return UpdatePlaceTypeCommand.Out(id = placeType.id, name = placeType.name)
    }

    override fun deletePlaceType(command: DeletePlaceTypeCommand.In) = placeTypeCommandOutputPort.deleteById(command.id)
}
