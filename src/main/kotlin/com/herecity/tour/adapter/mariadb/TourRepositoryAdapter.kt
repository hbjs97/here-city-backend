package com.herecity.tour.adapter.mariadb


import com.herecity.tour.application.port.output.TourOutputPort
import com.herecity.tour.domain.entity.Tour
import org.springframework.stereotype.Component

@Component
class TourRepositoryAdapter(
  private val mariaRepository: TourMariaRepository,
) : TourOutputPort {
  override fun getById(id: Long): Tour = mariaRepository.findById(id).orElseThrow()
  override fun findById(id: Long): Tour? = mariaRepository.findById(id).get()
  override fun save(project: Tour): Tour = mariaRepository.save(project)
}
