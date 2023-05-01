package com.herecity.tour.application.port.output

import com.herecity.tour.domain.entity.Tour

interface TourOutputPort {
  fun getById(id: Long): Tour
  fun findById(id: Long): Tour?
  fun save(tour: Tour): Tour
}