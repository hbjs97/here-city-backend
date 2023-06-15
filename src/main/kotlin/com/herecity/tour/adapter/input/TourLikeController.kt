package com.herecity.tour.adapter.input

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.tour.adapter.input.response.DisLikeTourResponse
import com.herecity.tour.adapter.input.response.LikeTourResponse
import com.herecity.tour.application.port.input.TourDisLikeCommand
import com.herecity.tour.application.port.input.TourLikeCommand
import com.herecity.user.domain.vo.UserDetail
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tours")
class TourLikeController(
    private val tourLikeCommand: TourLikeCommand,
    private val tourDisLikeCommand: TourDisLikeCommand,
) {
    @Authorize
    @Operation(summary = "투어 좋아요")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasUserRole)
    @PostMapping("{id}/like")
    fun likeTour(
        @PathVariable id: Long,
        @ReqUser user: UserDetail,
    ): LikeTourResponse = LikeTourResponse(tourLikeCommand.like(TourLikeCommand.In(id, user.getId())).liked)

    @Authorize
    @Operation(summary = "투어 좋아요 취소")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasUserRole)
    @PostMapping("{id}/dislike")
    fun disLikeTour(
        @PathVariable id: Long,
        @ReqUser user: UserDetail,
    ): DisLikeTourResponse = tourDisLikeCommand.disLike(TourDisLikeCommand.In(id, user.getId())).let {
        DisLikeTourResponse(it.liked)
    }
}
