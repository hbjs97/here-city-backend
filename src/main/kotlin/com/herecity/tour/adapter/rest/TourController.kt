package com.herecity.tour.adapter.rest

import com.herecity.common.annotation.ReqUser
import com.herecity.tour.application.dto.CreateTourDto
import com.herecity.tour.application.dto.TourPlaceDto
import com.herecity.tour.application.dto.TourPlanDto
import com.herecity.tour.application.dto.UpdateTourDto
import com.herecity.tour.application.dto.UpdateTourPlaceDto
import com.herecity.tour.application.port.input.AuthorizeTourUseCase
import com.herecity.tour.application.port.input.FetchTourUseCase
import com.herecity.tour.application.port.input.SaveTourUseCase
import com.herecity.user.application.security.Authorize
import com.herecity.user.domain.UserDetail
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/tours")
class TourController(
  private val fetchTourUseCase: FetchTourUseCase,
  private val saveTourUseCase: SaveTourUseCase,
  private val authorizeTourUseCase: AuthorizeTourUseCase,
) {
  @Operation(summary = "투어일정 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping("{tourId}/plan")
  fun fetchTourPlan(@PathVariable tourId: Long): TourPlanDto = this.fetchTourUseCase.fetchTourPlan(tourId)

  @Authorize
  @Operation(summary = "투어 생성")
  @ApiResponse(responseCode = "201")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority(\"USER\")")
  @PostMapping
  fun createPlace(@ReqUser user: UserDetail, @RequestBody createTourDto: CreateTourDto): TourPlanDto =
    this.saveTourUseCase.createTour(createTourDto, user.getId())

  @Authorize
  @Operation(summary = "투어일정 수정")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PatchMapping("{tourId}")
  fun updateTourPlan(
    @PathVariable tourId: Long,
    @ReqUser user: UserDetail,
    @RequestBody @Valid updateTourDto: UpdateTourDto,
  ): TourPlanDto {
    authorizeTourUseCase.checkHost(tourId, user.getId())
    return this.saveTourUseCase.updateTour(tourId, updateTourDto)
  }


  @Authorize
  @Operation(summary = "투어장소 수정")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PatchMapping("{tourId}/places/{placeId}")
  fun updateTourPlace(
    @PathVariable tourId: Long,
    @PathVariable placeId: Long,
    @ReqUser user: UserDetail,
    @RequestBody updateTourPlaceDto: UpdateTourPlaceDto,
  ): TourPlaceDto {
    authorizeTourUseCase.checkHost(tourId, user.getId())
    return this.saveTourUseCase.updateTourPlace(tourId, placeId, updateTourPlaceDto)
  }
}