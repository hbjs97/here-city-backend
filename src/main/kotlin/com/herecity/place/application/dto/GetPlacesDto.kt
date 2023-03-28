package com.herecity.place.application.dto

data class GetPlacesDto(
  val placeTypeId: Long?,

  val activityId: Long?,

  val unitId: Long?,

  val name: String?,
)
