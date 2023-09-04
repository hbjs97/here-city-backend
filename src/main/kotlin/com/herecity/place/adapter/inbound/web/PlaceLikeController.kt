package com.herecity.place.adapter.inbound.web

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.common.dto.OffSetPageable
import com.herecity.place.adapter.inbound.web.request.like.FetchMyPlacesRequest
import com.herecity.place.adapter.inbound.web.response.like.DisLikePlaceResponse
import com.herecity.place.adapter.inbound.web.response.like.FetchMyPlacesResponse
import com.herecity.place.adapter.inbound.web.response.like.LikePlaceResponse
import com.herecity.place.application.port.input.like.FetchMyPlacesQuery
import com.herecity.place.application.port.input.like.PlaceDisLikeCommand
import com.herecity.place.application.port.input.like.PlaceLikeCommand
import com.herecity.user.domain.vo.UserDetail
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/places")
class PlaceLikeController(
    private val fetchMyPlacesQuery: FetchMyPlacesQuery,
    private val placeLikeCommand: PlaceLikeCommand,
    private val placeDisLikeCommand: PlaceDisLikeCommand,
) {
    @Authorize
    @Operation(summary = "나의 장소 찜 리스트")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAllRoles)
    @GetMapping("likes/me")
    fun fetchMyPlaces(
        @ReqUser user: UserDetail,
        @Valid offSetPageable: OffSetPageable,
        fetchMyPlacesRequest: FetchMyPlacesRequest,
    ): FetchMyPlacesResponse = fetchMyPlacesQuery.fetchMyPlaces(
        fetchMyPlacesRequest.toDomain(
            userId = user.getId(),
            offSetPageable = offSetPageable,
        )
    ).let {
        FetchMyPlacesResponse(
            content = it.places,
            meta = it.meta,
        )
    }

    @Authorize
    @Operation(summary = "장소 좋아요")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasUserRole)
    @PostMapping("{id}/like")
    fun likePlace(
        @PathVariable id: Long,
        @ReqUser user: UserDetail,
    ): LikePlaceResponse = LikePlaceResponse(placeLikeCommand.like(PlaceLikeCommand.In(id, user.getId())).liked)

    @Authorize
    @Operation(summary = "장소 좋아요 취소")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasUserRole)
    @PostMapping("{id}/dislike")
    fun disLikePlace(
        @PathVariable id: Long,
        @ReqUser user: UserDetail,
    ): DisLikePlaceResponse = placeDisLikeCommand.disLike(PlaceDisLikeCommand.In(id, user.getId())).let {
        DisLikePlaceResponse(it.liked)
    }
}
