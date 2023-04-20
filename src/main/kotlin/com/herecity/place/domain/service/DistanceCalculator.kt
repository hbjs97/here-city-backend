package com.herecity.place.domain.service

import com.herecity.place.domain.vo.PositionVO
import org.springframework.stereotype.Service
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@Service
class DistanceCalculator {
  private val KOREA_RADIUS = 6371000.0 // Earth's radius for Korea in meters

  /**
   * longitude = x
   * latitude = y,
   * output is meters
   */
  fun measure(pos1: PositionVO, pos2: PositionVO): Double {
    val dLat = Math.toRadians(pos2.y - pos1.y)
    val dLon = Math.toRadians(pos2.x - pos1.x)
    val a = sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(pos1.y)) * cos(Math.toRadians(pos2.y)) * sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return KOREA_RADIUS * c
  }
}
