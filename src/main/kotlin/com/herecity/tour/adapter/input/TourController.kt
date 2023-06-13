package com.herecity.tour.adapter.input

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.common.dto.OffSetPageable
import com.herecity.tour.adapter.input.request.FetchToursRequest
import com.herecity.tour.adapter.input.response.FetchTourPlanResponse
import com.herecity.tour.adapter.input.response.FetchToursResponse
import com.herecity.tour.application.dto.CreateTourDto
import com.herecity.tour.application.dto.TourPlaceDto
import com.herecity.tour.application.dto.TourPlanDto
import com.herecity.tour.application.dto.UpdateTourDto
import com.herecity.tour.application.dto.UpdateTourPlaceDto
import com.herecity.tour.application.port.input.AuthorizeTourUseCase
import com.herecity.tour.application.port.input.FetchTourPlanQuery
import com.herecity.tour.application.port.input.FetchToursQuery
import com.herecity.tour.application.port.input.SaveTourUseCase
import com.herecity.user.domain.vo.UserDetail
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
    private val fetchTourPlanQuery: FetchTourPlanQuery,
    private val fetchToursQuery: FetchToursQuery,
    private val saveTourUseCase: SaveTourUseCase,
    private val authorizeTourUseCase: AuthorizeTourUseCase,
) {
    @Operation(summary = "투어목록 조회", description = "투어목록을 조회합니다. 평점 순으로 조회됩니다.")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun fetchTours(
        @Valid fetchToursRequest: FetchToursRequest,
        @Valid offSetPageable: OffSetPageable,
    ): FetchToursResponse = fetchToursQuery.fetchTours(
        fetchToursRequest.toDomain(offSetPageable)
    ).let {
        FetchToursResponse(
            content = it.tours,
            meta = it.meta,
        )
    }

    @Operation(summary = "투어일정 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{tourId}/plan")
    fun fetchTourPlan(@PathVariable tourId: Long): FetchTourPlanResponse =
        this.fetchTourPlanQuery.fetchTourPlan(
            FetchTourPlanQuery.In(id = tourId)
        ).let {
            FetchTourPlanResponse(
                id = it.id,
                ownerName = it.ownerName,
                tourName = it.tourName,
                regionName = it.regionName,
                scope = it.scope,
                from = it.from,
                to = it.to,
                notifications = it.notifications,
                tourPlaces = it.tourPlaces,
            )
        }

    @Authorize
    @Operation(summary = "투어 생성")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\"USER\")")
    @PostMapping
    fun createPlace(
        @ReqUser user: UserDetail,
        @RequestBody @Valid createTourDto: CreateTourDto,
    ): TourPlanDto =
        this.saveTourUseCase.createTour(createTourDto, user.getId())

    @Authorize
    @Operation(summary = "투어일정 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("{tourId}")
    fun updateTourPlan(
        @PathVariable tourId: Long,
        @ReqUser user: UserDetail,
        @RequestBody @Valid
        updateTourDto: UpdateTourDto,
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