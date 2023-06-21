package com.herecity.place.adapter.input

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.common.dto.OffSetPageable
import com.herecity.place.adapter.input.request.FetchPlacesPageRequest
import com.herecity.place.adapter.input.response.FetchPlacesPageResponse
import com.herecity.place.application.dto.CreatePlaceDto
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.FetchPlacesPageQuery
import com.herecity.place.application.port.input.RecordPlaceUseCase
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
    private val fetchPlacesPageQuery: FetchPlacesPageQuery,
    private val recordPlaceUseCase: RecordPlaceUseCase,
) {
    @Operation(summary = "장소 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun getPlaces(
        @ReqUser user: UserDetail,
        offSetPageable: OffSetPageable,
        fetchPlacesPageRequest: FetchPlacesPageRequest,
    ): FetchPlacesPageResponse = this.fetchPlacesPageQuery.fetchPlacesPage(
        fetchPlacesPageRequest.toDomain(
            user.getId(),
            offSetPageable,
        )
    ).let {
        FetchPlacesPageResponse(
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
    fun createPlace(@RequestBody createPlaceDto: CreatePlaceDto): PlaceDto =
        this.recordPlaceUseCase.createPlace(createPlaceDto)

    @Authorize
    @Operation(summary = "장소 정보 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PutMapping("{id}")
    fun updatePlace(@PathVariable id: Long, @RequestBody updatePlaceDto: CreatePlaceDto): PlaceDto {
        return this.recordPlaceUseCase.updatePlace(id, updatePlaceDto)
    }

    @Authorize
    @Operation(summary = "장소 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @DeleteMapping("{id}")
    fun deletePlace(@PathVariable id: Long) = this.recordPlaceUseCase.deletePlace(id)
}
