package com.herecity.unit.application.port.service

import com.herecity.activity.domain.exception.DuplicateActivityNameException
import com.herecity.unit.application.port.inbound.CreateUnitCommand
import com.herecity.unit.application.port.inbound.DeleteUnitCommand
import com.herecity.unit.application.port.inbound.UpdateUnitCommand
import com.herecity.unit.application.port.outbound.UnitCommandOutputPort
import com.herecity.unit.application.port.outbound.UnitQueryOutputPort
import com.herecity.unit.domain.entity.Unit
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UnitCommandService(
    private val unitQueryOutputPort: UnitQueryOutputPort,
    private val unitCommandOutputPort: UnitCommandOutputPort,
) : CreateUnitCommand, UpdateUnitCommand, DeleteUnitCommand {
    @Transactional
    override fun createUnit(command: CreateUnitCommand.In): CreateUnitCommand.Out {
        val exist = unitQueryOutputPort.findByName(command.name) !== null
        if (exist) throw DuplicateActivityNameException()
        val unit = unitCommandOutputPort.save(Unit(name = command.name))
        return CreateUnitCommand.Out(
            id = unit.id,
            name = unit.name,
        )
    }

    @Transactional
    override fun updateUnit(command: UpdateUnitCommand.In): UpdateUnitCommand.Out {
        val unit = unitQueryOutputPort.getById(command.id)
        unit.name = command.name
        unitCommandOutputPort.save(unit)
        return UpdateUnitCommand.Out(
            id = unit.id,
            name = unit.name,
        )
    }
    
    @Transactional
    override fun deleteUnit(command: DeleteUnitCommand.In) {
        unitCommandOutputPort.deleteById(command.id)
    }
}
