package com.herecity.place.adapter.input

import com.herecity.common.annotation.Authorize
import com.herecity.place.application.dto.CreatePlaceTypeDto
import com.herecity.place.application.dto.PlaceTypeDto
import com.herecity.place.application.port.input.FetchPlaceTypeUseCase
import com.herecity.place.application.port.input.RecordPlaceTypeUseCase
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/places/types")
class PlaceTypeController(
    private val fetchPlaceTypeUseCase: FetchPlaceTypeUseCase,
    private val recordPlaceTypeUseCase: RecordPlaceTypeUseCase,
) {
    @Operation(summary = "장소분류 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun getPlaceTypes(pageable: Pageable): Page<PlaceTypeDto> =
        this.fetchPlaceTypeUseCase.getPlaceTypes(pageable)

    @Authorize
    @Operation(summary = "장소분류 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PostMapping
    fun createPlaceType(@RequestBody createPlaceTypeDto: CreatePlaceTypeDto): PlaceTypeDto =
        this.recordPlaceTypeUseCase.createPlaceType(createPlaceTypeDto)

    @Authorize
    @Operation(summary = "장소분류 정보 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PutMapping("{id}")
    fun updatePlaceType(@PathVariable id: Long, @RequestBody updatePlaceDto: CreatePlaceTypeDto): PlaceTypeDto =
        this.recordPlaceTypeUseCase.updatePlaceType(id, updatePlaceDto.name)

    @Authorize
    @Operation(summary = "장소분류 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @DeleteMapping("{id}")
    fun deletePlaceType(@PathVariable id: Long) = this.recordPlaceTypeUseCase.deletePlaceType(id)
}
