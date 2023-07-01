package com.herecity.place.adapter.input

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.common.converter.LocalTimeConverter
import com.herecity.common.dto.OffSetPageable
import com.herecity.place.adapter.input.request.CreateReviewRequest
import com.herecity.place.adapter.input.request.FetchMyReviewsRequest
import com.herecity.place.adapter.input.request.FetchReviewsRequest
import com.herecity.place.adapter.input.response.CreateReviewResponse
import com.herecity.place.adapter.input.response.FetchMyReviewsResponse
import com.herecity.place.adapter.input.response.FetchReviewsResponse
import com.herecity.place.application.port.input.CreatePlaceReviewCommand
import com.herecity.place.application.port.input.FetchMyReviewsQuery
import com.herecity.place.application.port.input.FetchReviewsQuery
import com.herecity.user.domain.vo.UserDetail
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/places/reviews")
class PlaceReviewController(
    private val fetchReviewsQuery: FetchReviewsQuery,
    private val fetchMyReviewsQuery: FetchMyReviewsQuery,
    private val createPlaceReviewCommand: CreatePlaceReviewCommand,
) {
    @Operation(summary = "리뷰 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun fetchReviews(
        fetchReviewsRequest: FetchReviewsRequest,
        offSetPageable: OffSetPageable,
    ): FetchReviewsResponse =
        this.fetchReviewsQuery.fetchReviews(
            fetchReviewsRequest.toDomain(
                offSetPageable = offSetPageable,
            )
        ).let {
            FetchReviewsResponse(
                content = it.reviews,
                meta = it.meta,
            )
        }

    @Authorize
    @Operation(summary = "나의 리뷰 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAllRoles)
    @GetMapping("me")
    fun fetchMyReviews(
        @ReqUser user: UserDetail,
        fetchMyReviewsRequest: FetchMyReviewsRequest,
        offSetPageable: OffSetPageable,
    ): FetchMyReviewsResponse =
        this.fetchMyReviewsQuery.fetchMyReviews(
            fetchMyReviewsRequest.toDomain(
                userId = user.getId(),
                offSetPageable = offSetPageable,
            )
        ).let {
            FetchMyReviewsResponse(
                content = it.reviews,
                meta = it.meta,
            )
        }

    @Authorize
    @Operation(summary = "리뷰 작성")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAllRoles)
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun review(
        @ReqUser user: UserDetail,
        @ModelAttribute createReviewRequest: CreateReviewRequest,
    ): CreateReviewResponse =
        this.createPlaceReviewCommand.review(createReviewRequest.toDomain(user.getId())).let {
            CreateReviewResponse(
                id = it.id,
                placeId = it.placeId,
                tourId = it.tourId,
                rating = it.rating,
                content = it.content,
                images = it.images,
                createdAt = LocalTimeConverter.convert(it.createdAt),
                createdBy = it.createdBy,
                userDisplayName = it.userDisplayName,
                userThumbnail = it.userThumbnail,
            )
        }
}
