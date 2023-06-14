package com.herecity.place.adapter.input

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.place.application.dto.CreateReviewDto
import com.herecity.place.application.dto.GetReviewsDto
import com.herecity.place.application.dto.PlaceReviewDto
import com.herecity.place.application.port.input.FetchPlaceReviewUseCase
import com.herecity.place.application.port.input.RecordPlaceReviewUseCase
import com.herecity.user.domain.vo.UserDetail
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/places/reviews")
class PlaceReviewController(
    private val fetchPlaceReviewUseCase: FetchPlaceReviewUseCase,
    private val recordPlaceReviewUseCase: RecordPlaceReviewUseCase,
) {
    @Operation(summary = "리뷰 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun getPlaceReviews(
        getReviewsDto: GetReviewsDto,
        pageable: Pageable,
    ): Page<PlaceReviewDto> =
        this.fetchPlaceReviewUseCase.getPlaceReviews(getReviewsDto, pageable)

    @Authorize
    @Operation(summary = "리뷰 작성")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAllRoles)
    @PostMapping
    fun review(
        @ReqUser user: UserDetail,
        @RequestBody createReviewDto: CreateReviewDto,
    ): PlaceReviewDto =
        this.recordPlaceReviewUseCase.review(user.getId(), createReviewDto)
}
