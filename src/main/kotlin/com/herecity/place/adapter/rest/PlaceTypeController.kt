package com.herecity.place.adapter.rest

import com.herecity.common.annotation.ReqUser
import com.herecity.place.application.dto.CreatePlaceTypeDto
import com.herecity.place.application.dto.PlaceTypeDto
import com.herecity.place.application.port.input.LoadPlaceTypeUseCase
import com.herecity.place.application.port.input.RecordPlaceTypeUseCase
import com.herecity.user.domain.UserDetail
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
  private val loadPlaceTypeUseCase: LoadPlaceTypeUseCase,
  private val recordPlaceTypeUseCase: RecordPlaceTypeUseCase
) {
  @Operation(summary = "장소분류 목록 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\", \"USER\")")
  @GetMapping
  fun getPlaceTypes(@ReqUser user: UserDetail, pageable: Pageable): Page<PlaceTypeDto> = this.loadPlaceTypeUseCase.getPlaceTypes(pageable)

  @Operation(summary = "장소분류 등록")
  @ApiResponse(responseCode = "201")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PostMapping
  fun createPlaceType(@RequestBody createPlaceTypeDto: CreatePlaceTypeDto): PlaceTypeDto = this.recordPlaceTypeUseCase.createPlaceType(createPlaceTypeDto)

  @Operation(summary = "장소분류 정보 수정")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PutMapping("{id}")
  fun updatePlaceType(@PathVariable id: Long, @RequestBody updatePlaceDto: CreatePlaceTypeDto): PlaceTypeDto = this.recordPlaceTypeUseCase.updatePlaceType(id, updatePlaceDto.name)

  @Operation(summary = "장소분류 삭제")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @DeleteMapping("{id}")
  fun deletePlaceType(@PathVariable id: Long) = this.recordPlaceTypeUseCase.deletePlaceType(id)
}
