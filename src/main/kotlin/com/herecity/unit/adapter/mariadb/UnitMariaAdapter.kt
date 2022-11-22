package com.herecity.unit.adapter.mariadb

import com.herecity.member.domain.entity.Unit
import com.herecity.unit.application.port.output.UnitCommandOutputPort
import com.herecity.unit.application.port.output.UnitQueryOutputPort
import com.herecity.unit.domain.exception.DuplicateUnitNameException
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class UnitMariaAdapter(
  private val unitRepository: UnitRepository
) : UnitQueryOutputPort, UnitCommandOutputPort {
  override fun findAll(): List<Unit> = this.unitRepository.findAll()

  override fun getById(id: Long): Unit = this.unitRepository.findById(id).orElseThrow()

  override fun findById(id: Long): Unit? = this.unitRepository.findById(id).get()

  override fun save(unit: Unit): Unit {
    try {
      return this.unitRepository.save(unit)
    } catch (e: DataIntegrityViolationException) {
      throw DuplicateUnitNameException()
    }
  }

  override fun findByName(name: String): Unit? = this.unitRepository.findByName(name)

  override fun deleteById(id: Long) {
    val unit = this.getById(id)
    this.unitRepository.delete(unit)
  }

}
