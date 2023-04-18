package com.herecity.place.application.service

import com.herecity.place.application.port.input.PlaceLikeUseCase
import org.springframework.stereotype.Service

@Service
class PlaceLikeService() : PlaceLikeUseCase {
  override fun like(placeId: Long): Boolean {
    TODO("Not yet implemented")
  }

  override fun disLike(placeId: Long): Boolean {
    TODO("Not yet implemented")
  }

}
