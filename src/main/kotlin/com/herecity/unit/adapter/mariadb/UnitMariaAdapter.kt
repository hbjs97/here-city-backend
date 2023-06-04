package com.herecity.unit.adapter.mariadb

import com.herecity.unit.application.port.output.UnitCommandOutputPort
import com.herecity.unit.application.port.output.UnitQueryOutputPort
import com.herecity.unit.domain.entity.Unit
import org.springframework.stereotype.Component
import org.webjars.NotFoundException

@Component
class UnitMariaAdapter(
    private val unitRepository: UnitRepository,
) : UnitQueryOutputPort, UnitCommandOutputPort {
    override fun findAll(): List<Unit> = this.unitRepository.findAll()

    override fun getById(id: Long): Unit = this.unitRepository.findById(id).orElseThrow()

    override fun findById(id: Long): Unit? = this.unitRepository.findById(id).get()

    override fun save(entity: Unit): Unit = this.unitRepository.save(entity)

    override fun findByName(name: String): Unit? = this.unitRepository.findByName(name)
    override fun getByIds(ids: List<Long>): List<Unit> {
        val units = this.unitRepository.findAllById(ids)
        val notFoundedIds = ids.subtract(units.map { v -> v.id }.toSet())
        if (notFoundedIds.isNotEmpty()) throw NotFoundException("id가 존재하지 않습니다. [$notFoundedIds]")
        return units
    }

    override fun deleteById(id: Long) {
        val unit = this.getById(id)
        this.unitRepository.delete(unit)
    }
}
