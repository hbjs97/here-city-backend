package com.herecity.place.adapter.inbound.web

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.common.dto.OffSetPageable
import com.herecity.place.adapter.inbound.web.request.place.CreatePlaceRequest
import com.herecity.place.adapter.inbound.web.request.place.FetchNearByPlacesPageRequest
import com.herecity.place.adapter.inbound.web.request.place.FetchRecommendPlacesPageRequest
import com.herecity.place.adapter.inbound.web.request.place.UpdatePlaceRequest
import com.herecity.place.adapter.inbound.web.response.place.CreatePlaceResponse
import com.herecity.place.adapter.inbound.web.response.place.FetchNearByPlacesPageResponse
import com.herecity.place.adapter.inbound.web.response.place.FetchRecommendPlacesPageResponse
import com.herecity.place.adapter.inbound.web.response.place.UpdatePlaceResponse
import com.herecity.place.application.port.input.place.CreatePlaceCommand
import com.herecity.place.application.port.input.place.DeletePlaceCommand
import com.herecity.place.application.port.input.place.FetchNearByPlacesPageQuery
import com.herecity.place.application.port.input.place.FetchRecommendPlacesPageQuery
import com.herecity.place.application.port.input.place.UpdatePlaceCommand
import com.herecity.user.domain.vo.UserDetail
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/places")
class PlaceController(
    private val fetchRecommendPlacesPageQuery: FetchRecommendPlacesPageQuery,
    private val fetchNearByPlacesPageQuery: FetchNearByPlacesPageQuery,
    private val createPlaceCommand: CreatePlaceCommand,
    private val updatePlaceCommand: UpdatePlaceCommand,
    private val deletePlaceCommand: DeletePlaceCommand,
) {
    @Authorize
    @Operation(summary = "주변 장소 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun fetchNearByPlaces(
        @ReqUser user: UserDetail,
        offSetPageable: OffSetPageable,
        fetchNearByPlacesPageRequest: FetchNearByPlacesPageRequest,
    ): FetchNearByPlacesPageResponse = fetchNearByPlacesPageQuery.fetchNearByPlacesPage(
        fetchNearByPlacesPageRequest.toDomain(
            user.getId(),
            offSetPageable,
        )
    ).let {
        FetchNearByPlacesPageResponse(
            content = it.places,
            meta = it.meta,
        )
    }

    @Authorize
    @Operation(summary = "추천 장소 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("recommends")
    fun fetchRecommendPlaces(
        @ReqUser user: UserDetail,
        offSetPageable: OffSetPageable,
        fetchRecommendPlacesPageRequest: FetchRecommendPlacesPageRequest,
    ): FetchRecommendPlacesPageResponse = fetchRecommendPlacesPageQuery.fetchRecommendPlacesPage(
        fetchRecommendPlacesPageRequest.toDomain(
            user.getId(),
            offSetPageable,
        )
    ).let {
        FetchRecommendPlacesPageResponse(
            content = it.places,
            meta = it.meta,
        )
    }

    @Authorize
    @Operation(summary = "장소 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PostMapping
    fun createPlace(@RequestBody createPlaceRequest: CreatePlaceRequest): CreatePlaceResponse =
        CreatePlaceResponse.from(createPlaceCommand.createPlace(createPlaceRequest.toDomain()))

    @Authorize
    @Operation(summary = "장소 정보 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PutMapping("{id}")
    fun updatePlace(@PathVariable id: Long, @RequestBody updatePlaceRequest: UpdatePlaceRequest): UpdatePlaceResponse =
        UpdatePlaceResponse.from(updatePlaceCommand.updatePlace(updatePlaceRequest.toDomain(id)))

    @Authorize
    @Operation(summary = "장소 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @DeleteMapping("{id}")
    fun deletePlace(@PathVariable id: Long) = deletePlaceCommand.deletePlace(DeletePlaceCommand.In(id))
}
