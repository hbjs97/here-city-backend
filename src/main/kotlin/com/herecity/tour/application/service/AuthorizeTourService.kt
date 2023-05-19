package com.herecity.tour.application.service

import com.herecity.tour.application.port.input.AuthorizeTourUseCase
import com.herecity.tour.application.port.output.TourOutputPort
import com.herecity.tour.domain.exception.IsNotTourHostException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthorizeTourService(private val tourOutputPort: TourOutputPort) : AuthorizeTourUseCase {
  override fun checkHost(id: Long, userId: UUID) {
    tourOutputPort.getById(id).run {
      if (!isHost(userId)) throw IsNotTourHostException()
    }
  }
}