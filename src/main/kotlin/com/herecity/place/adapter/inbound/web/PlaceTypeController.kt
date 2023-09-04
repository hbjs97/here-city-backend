package com.herecity.place.adapter.inbound.web

import com.herecity.common.annotation.Authorize
import com.herecity.common.dto.OffSetPageable
import com.herecity.place.adapter.inbound.web.request.type.CreatePlaceTypeRequest
import com.herecity.place.adapter.inbound.web.request.type.UpdatePlaceTypeRequest
import com.herecity.place.adapter.inbound.web.response.type.CreatePlaceTypeResponse
import com.herecity.place.adapter.inbound.web.response.type.FetchPlaceTypesResponse
import com.herecity.place.adapter.inbound.web.response.type.UpdatePlaceTypeResponse
import com.herecity.place.application.port.input.type.CreatePlaceTypeCommand
import com.herecity.place.application.port.input.type.DeletePlaceTypeCommand
import com.herecity.place.application.port.input.type.FetchPlaceTypesQuery
import com.herecity.place.application.port.input.type.UpdatePlaceTypeCommand
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/places/types")
class PlaceTypeController(
    private val fetchPlaceTypesQuery: FetchPlaceTypesQuery,
    private val createPlaceTypeCommand: CreatePlaceTypeCommand,
    private val updatePlaceTypeCommand: UpdatePlaceTypeCommand,
    private val deletePlaceTypeCommand: DeletePlaceTypeCommand,
) {
    @Operation(summary = "장소분류 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun fetchPlaceTypes(offSetPageable: OffSetPageable): FetchPlaceTypesResponse =
        FetchPlaceTypesResponse(fetchPlaceTypesQuery.fetchPlaceTypes(FetchPlaceTypesQuery.In(offSetPageable)).pages)

    @Authorize
    @Operation(summary = "장소분류 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PostMapping
    fun createPlaceType(@RequestBody createPlaceTypeRequest: CreatePlaceTypeRequest): CreatePlaceTypeResponse =
        CreatePlaceTypeResponse.from(createPlaceTypeCommand.createPlaceType(createPlaceTypeRequest.toDomain()))

    @Authorize
    @Operation(summary = "장소분류 정보 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PutMapping("{id}")
    fun updatePlaceType(@PathVariable id: Long, @RequestBody updatePlaceTypeRequest: UpdatePlaceTypeRequest): UpdatePlaceTypeResponse =
        UpdatePlaceTypeResponse.from(updatePlaceTypeCommand.updatePlaceType(updatePlaceTypeRequest.toDomain(id)))

    @Authorize
    @Operation(summary = "장소분류 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @DeleteMapping("{id}")
    fun deletePlaceType(@PathVariable id: Long) = deletePlaceTypeCommand.deletePlaceType(DeletePlaceTypeCommand.In(id))
}
