package com.herecity.place.adapter.rest

import com.herecity.common.annotation.ReqUser
import com.herecity.place.adapter.rest.response.DisLikePlaceResponse
import com.herecity.place.adapter.rest.response.LikePlaceResponse
import com.herecity.place.application.port.input.PlaceDisLikeCommand
import com.herecity.place.application.port.input.PlaceLikeCommand
import com.herecity.user.application.security.Authorize
import com.herecity.user.domain.UserDetail
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/places")
class PlaceLikeController(
    private val placeLikeCommand: PlaceLikeCommand,
    private val placeDisLikeCommand: PlaceDisLikeCommand,
) {
    @Authorize
    @Operation(summary = "장소 좋아요")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\"USER\")")
    @PostMapping("{id}/like")
    fun likePlace(
        @PathVariable id: Long,
        @ReqUser user: UserDetail,
    ): LikePlaceResponse = placeLikeCommand.like(PlaceLikeCommand.In(id, user.getId())).let {
        LikePlaceResponse(it.liked)
    }

    @Authorize
    @Operation(summary = "장소 좋아요 취소")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\"USER\")")
    @PostMapping("{id}/dislike")
    fun disLikePlace(
        @PathVariable id: Long,
        @ReqUser user: UserDetail,
    ): DisLikePlaceResponse = placeDisLikeCommand.disLike(PlaceDisLikeCommand.In(id, user.getId())).let {
        DisLikePlaceResponse(it.liked)
    }
}
