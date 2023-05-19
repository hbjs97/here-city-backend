package com.herecity.tour.application.port.input

import java.util.*

interface AuthorizeTourUseCase {
  fun checkHost(id: Long, userId: UUID)
}