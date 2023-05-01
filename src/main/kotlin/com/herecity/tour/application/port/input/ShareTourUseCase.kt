package com.herecity.tour.application.port.input

interface ShareTourUseCase {
  fun shareJoinCode(id: Long): String
}