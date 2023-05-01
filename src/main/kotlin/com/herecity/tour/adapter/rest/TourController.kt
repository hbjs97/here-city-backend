package com.herecity.tour.adapter.rest

import com.herecity.common.annotation.ReqUser
import com.herecity.tour.application.dto.CreateTourDto
import com.herecity.tour.application.dto.TourPlanDto
import com.herecity.tour.application.port.input.FetchTourUseCase
import com.herecity.tour.application.port.input.SaveTourUseCase
import com.herecity.user.application.security.Authorize
import com.herecity.user.domain.UserDetail
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/tours")
class TourController(
  private val fetchTourUseCase: FetchTourUseCase,
  private val saveTourUseCase: SaveTourUseCase
) {
  @Operation(summary = "투어일정 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping("{id}/plan")
  fun fetchTourPlan(@PathVariable id: Long): TourPlanDto = this.fetchTourUseCase.fetchTourPlan(id)

  @Authorize
  @Operation(summary = "투어 생성")
  @ApiResponse(responseCode = "201")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority(\"USER\")")
  @PostMapping
  fun createPlace(@ReqUser user: UserDetail, @RequestBody createTourDto: CreateTourDto) =
    this.saveTourUseCase.createTour(createTourDto, user.getId())
}