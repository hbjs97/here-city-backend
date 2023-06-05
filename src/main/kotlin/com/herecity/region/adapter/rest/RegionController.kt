package com.herecity.region.adapter.rest

import com.herecity.region.adapter.dto.NameDto
import com.herecity.region.adapter.dto.RegionDto
import com.herecity.region.application.dto.UpdateRegionDto
import com.herecity.region.application.port.input.FetchRegionUseCase
import com.herecity.region.application.port.input.RecordRegionUseCase
import com.herecity.user.application.security.Authorize
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/regions")
class RegionController(
    private val fetchRegionUseCase: FetchRegionUseCase,
    private val recordRegionUseCase: RecordRegionUseCase,
) {
    @Operation(summary = "상위 지역 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun getUpperRegions(): List<RegionDto> = this.fetchRegionUseCase.getUpperRegions()

    @Operation(summary = "하위 지역 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    fun getSubRegions(@PathVariable id: Long): List<RegionDto> =
        this.fetchRegionUseCase.getSubRegions(id)

    @Authorize
    @Operation(summary = "상위 지역 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
    @PostMapping
    fun createUpperRegion(@RequestBody nameDto: NameDto): RegionDto =
        this.recordRegionUseCase.createUpperRegion(nameDto.name)

    @Authorize
    @Operation(summary = "하위 지역 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
    @PostMapping("{id}")
    fun addSubRegion(@PathVariable id: Long, @RequestBody nameDto: NameDto): RegionDto =
        this.recordRegionUseCase.addSubRegion(id, nameDto.name)

    @Authorize
    @Operation(summary = "지역 정보 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
    @PatchMapping("{id}")
    fun updateCity(@PathVariable id: Long, @RequestBody updateRegionDto: UpdateRegionDto): RegionDto {
        if (updateRegionDto.upperRegionId == null && updateRegionDto.name == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "지역 수정 시 파라미터가 모두 null 일 수 없습니다.")
        }
        return this.recordRegionUseCase.updateRegion(id, updateRegionDto)
    }

    @Authorize
    @Operation(summary = "지역 삭제", description = "하위 지역이 존재하면 삭제할 수 없습니다.")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
    @DeleteMapping("{id}")
    fun deleteCity(@PathVariable id: Long) = this.recordRegionUseCase.deleteRegion(id)
}
