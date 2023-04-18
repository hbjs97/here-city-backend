package com.herecity.place.application.port.input

interface PlaceLikeUseCase {
  fun like(placeId: Long): Boolean
  fun disLike(placeId: Long): Boolean
}
