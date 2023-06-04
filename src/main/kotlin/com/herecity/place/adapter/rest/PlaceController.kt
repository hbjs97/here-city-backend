package com.herecity.place.adapter.rest

import com.herecity.place.application.dto.CreatePlaceDto
import com.herecity.place.application.dto.GetPlacesDto
import com.herecity.place.application.dto.PlaceDto
import com.herecity.place.application.port.input.FetchPlaceUseCase
import com.herecity.place.application.port.input.RecordPlaceUseCase
import com.herecity.user.application.security.Authorize
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
    private val fetchPlaceUseCase: FetchPlaceUseCase,
    private val recordPlaceUseCase: RecordPlaceUseCase,
) {
    @Authorize
    @Operation(summary = "장소 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\"ADMIN\", \"USER\")")
    @GetMapping
    fun getPlaces(
        @PageableDefault(page = 0, size = 10) pageable: Pageable,
        getPlacesDto: GetPlacesDto,
    ): Page<PlaceDto> =
        this.fetchPlaceUseCase.getPlaces(getPlacesDto, pageable)

    @Authorize
    @Operation(summary = "장소 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
    @PostMapping
    fun createPlace(@RequestBody createPlaceDto: CreatePlaceDto): PlaceDto =
        this.recordPlaceUseCase.createPlace(createPlaceDto)

    @Authorize
    @Operation(summary = "장소 정보 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
    @PutMapping("{id}")
    fun updatePlace(@PathVariable id: Long, @RequestBody updatePlaceDto: CreatePlaceDto): PlaceDto {
        return this.recordPlaceUseCase.updatePlace(id, updatePlaceDto)
    }

    @Authorize
    @Operation(summary = "장소 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
    @DeleteMapping("{id}")
    fun deletePlace(@PathVariable id: Long) = this.recordPlaceUseCase.deletePlace(id)
}
