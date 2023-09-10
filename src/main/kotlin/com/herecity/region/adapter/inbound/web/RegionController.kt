package com.herecity.region.adapter.inbound.web

import com.herecity.common.annotation.Authorize
import com.herecity.region.adapter.inbound.web.request.CreateRegionRequest
import com.herecity.region.adapter.inbound.web.request.UpdateRegionRequest
import com.herecity.region.adapter.inbound.web.response.CreateRegionResponse
import com.herecity.region.adapter.inbound.web.response.FetchRegionsResponse
import com.herecity.region.adapter.inbound.web.response.UpdateRegionResponse
import com.herecity.region.application.port.inbound.CreateRegionCommand
import com.herecity.region.application.port.inbound.FetchAllRegionsQuery
import com.herecity.region.application.port.inbound.UpdateRegionCommand
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/regions")
class RegionController(
    private val fetchAllRegionsQuery: FetchAllRegionsQuery,
    private val createRegionCommand: CreateRegionCommand,
    private val updateRegionCommand: UpdateRegionCommand,
) {
    @Operation(summary = "지역 목록 조회")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun fetchRegions(): FetchRegionsResponse = fetchAllRegionsQuery.fetchRegions().let {
        FetchRegionsResponse(contents = it.regions)
    }

    @Authorize
    @Operation(summary = "지역 등록")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PostMapping
    fun createUpperRegion(
        @RequestBody @Valid
        createRegionRequest: CreateRegionRequest,
    ): CreateRegionResponse =
        createRegionCommand.createRegion(createRegionRequest.toDomain()).let {
            CreateRegionResponse(id = it.id, name = it.name)
        }

    @Authorize
    @Operation(summary = "지역 정보 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PatchMapping("{id}")
    fun updateCity(@PathVariable id: Long, @RequestBody updateRegionRequest: UpdateRegionRequest): UpdateRegionResponse =
        updateRegionCommand.updateRegion(updateRegionRequest.toDomain(id)).let {
            UpdateRegionResponse(id = it.id, name = it.name)
        }
}
