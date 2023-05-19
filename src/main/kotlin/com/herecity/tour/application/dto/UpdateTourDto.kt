package com.herecity.tour.application.dto

import com.herecity.tour.domain.vo.Scope
import java.time.LocalDateTime
import javax.validation.constraints.Size

data class UpdateTourDto(
  @field:Size(max = 50)
  val name: String? = null,
  val scope: Scope? = null,
  val from: LocalDateTime? = null,
  val to: LocalDateTime? = null,
)
