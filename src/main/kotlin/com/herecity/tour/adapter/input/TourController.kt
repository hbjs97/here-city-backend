package com.herecity.tour.adapter.input

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.common.converter.LocalTimeConverter
import com.herecity.common.dto.OffSetPageable
import com.herecity.tour.adapter.input.request.CreateTourRequest
import com.herecity.tour.adapter.input.request.FetchMyToursRequest
import com.herecity.tour.adapter.input.request.FetchToursRequest
import com.herecity.tour.adapter.input.request.UpdateTourPlaceRequest
import com.herecity.tour.adapter.input.request.UpdateTourRequest
import com.herecity.tour.adapter.input.response.CreateTourResponse
import com.herecity.tour.adapter.input.response.FetchMyToursResponse
import com.herecity.tour.adapter.input.response.FetchTourPlacesReviewResponse
import com.herecity.tour.adapter.input.response.FetchTourPlanResponse
import com.herecity.tour.adapter.input.response.FetchToursResponse
import com.herecity.tour.adapter.input.response.UpdateTourPlaceResponse
import com.herecity.tour.adapter.input.response.UpdateTourResponse
import com.herecity.tour.application.port.input.AuthorizeTourUseCase
import com.herecity.tour.application.port.input.CreateTourCommand
import com.herecity.tour.application.port.input.FetchMyToursQuery
import com.herecity.tour.application.port.input.FetchTourPlacesReviewQuery
import com.herecity.tour.application.port.input.FetchTourPlanQuery
import com.herecity.tour.application.port.input.FetchToursQuery
import com.herecity.tour.application.port.input.UpdateTourCommand
import com.herecity.tour.application.port.input.UpdateTourPlaceCommand
import com.herecity.user.domain.vo.UserDetail
import com.herecity.user.domain.vo.UserRole
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
    private val fetchMyToursQuery: FetchMyToursQuery,
    private val fetchTourPlacesReviewQuery: FetchTourPlacesReviewQuery,
    private val createTourCommand: CreateTourCommand,
    private val updateTourCommand: UpdateTourCommand,
    private val updateTourPlaceCommand: UpdateTourPlaceCommand,
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

    @Authorize
    @Operation(summary = "나의 투어목록 조회", description = "나의 투어목록을 조회합니다.")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasUserRole)
    @GetMapping("me")
    fun fetchMyTours(
        @ReqUser user: UserDetail,
        @Valid fetchMyToursRequest: FetchMyToursRequest,
        @Valid offSetPageable: OffSetPageable,
    ): FetchMyToursResponse = fetchMyToursQuery.fetchMyTours(
        fetchMyToursRequest.toDomain(
            userId = user.getId(),
            offSetPageable = offSetPageable
        )
    ).let {
        FetchMyToursResponse(
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
    @Operation(summary = "투어장소 리뷰조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasUserRole)
    @GetMapping("{tourId}/reviews")
    fun fetchTourPlacesReview(@PathVariable tourId: Long, @ReqUser user: UserDetail): FetchTourPlacesReviewResponse =
        FetchTourPlacesReviewResponse.from(
            fetchTourPlacesReviewQuery.fetchReviews(
                FetchTourPlacesReviewQuery.In(tourId = tourId, userId = user.getId())
            )
        )

    @Authorize
    @Operation(summary = "투어 생성")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasUserRole)
    @PostMapping
    fun createPlace(
        @ReqUser user: UserDetail,
        @RequestBody @Valid createTourRequest: CreateTourRequest,
    ): CreateTourResponse =
        this.createTourCommand.createTour(createTourRequest.toDomain(user.getId())).let {
            CreateTourResponse(
                id = it.id,
                ownerName = it.ownerName,
                tourName = it.tourName,
                regionName = it.regionName,
                scope = it.scope,
                from = LocalTimeConverter.convert(it.from),
                to = LocalTimeConverter.convert(it.to),
                tourPlaces = it.tourPlaces,
            )
        }

    @Authorize
    @Operation(summary = "투어일정 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("{tourId}")
    fun updateTourPlan(
        @PathVariable tourId: Long,
        @ReqUser user: UserDetail,
        @RequestBody @Valid
        updateTourRequest: UpdateTourRequest,
    ): UpdateTourResponse {
        authorizeTourUseCase.checkHost(tourId, user.getId())
        return this.updateTourCommand.updateTour(updateTourRequest.toDomain(tourId)).let {
            UpdateTourResponse(
                id = it.id,
                ownerName = it.ownerName,
                tourName = it.tourName,
                regionName = it.regionName,
                scope = it.scope,
                from = LocalTimeConverter.convert(it.from),
                to = LocalTimeConverter.convert(it.to),
                tourPlaces = it.tourPlaces,
            )
        }
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
        @RequestBody updateTourPlaceRequest: UpdateTourPlaceRequest,
    ): UpdateTourPlaceResponse {
        authorizeTourUseCase.checkHost(tourId, user.getId())
        return this.updateTourPlaceCommand.updateTourPlace(
            updateTourPlaceRequest.toDomain(
                tourId = tourId,
                placeId = placeId,
            )
        ).let {
            UpdateTourPlaceResponse(
                place = it.place,
                from = LocalTimeConverter.convert(it.from),
                to = LocalTimeConverter.convert(it.to),
                budgets = it.budgets,
            )
        }
    }
}
