package com.herecity.unit.adapter.inbound.web

import com.herecity.common.annotation.Authorize
import com.herecity.unit.adapter.inbound.web.request.CreateUnitRequest
import com.herecity.unit.adapter.inbound.web.request.UpdateUnitRequest
import com.herecity.unit.adapter.inbound.web.response.CreateUnitResponse
import com.herecity.unit.adapter.inbound.web.response.FetchUnitsResponse
import com.herecity.unit.adapter.inbound.web.response.UpdateUnitResponse
import com.herecity.unit.application.port.inbound.CreateUnitCommand
import com.herecity.unit.application.port.inbound.DeleteUnitCommand
import com.herecity.unit.application.port.inbound.FetchUnitsQuery
import com.herecity.unit.application.port.inbound.UpdateUnitCommand
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/units")
class UnitController(
    private val fetchUnitsQuery: FetchUnitsQuery,
    private val createUnitCommand: CreateUnitCommand,
    private val updateUnitCommand: UpdateUnitCommand,
    private val deleteUnitCommand: DeleteUnitCommand,
) {
    @Operation(summary = "유닛 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun fetchUnits(): FetchUnitsResponse = fetchUnitsQuery.fetchUnits().let {
        FetchUnitsResponse(contents = it.units)
    }

    @Authorize
    @Operation(summary = "유닛 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PostMapping
    fun createUnit(
        @RequestBody @Valid
        createUnitRequest: CreateUnitRequest,
    ): CreateUnitResponse =
        createUnitCommand.createUnit(createUnitRequest.toDomain()).let {
            CreateUnitResponse(it.id, it.name)
        }

    @Authorize
    @Operation(summary = "유닛 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PatchMapping("{id}")
    fun updateUnit(
        @PathVariable id: Long,
        @RequestBody @Valid
        updateUnitRequest: UpdateUnitRequest,
    ): UpdateUnitResponse =
        updateUnitCommand.updateUnit(updateUnitRequest.toDomain(id)).let {
            UpdateUnitResponse(id = it.id, name = it.name)
        }

    @Authorize
    @Operation(summary = "유닛 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @DeleteMapping("{id}")
    fun deleteUnit(@PathVariable id: Long) = deleteUnitCommand.deleteUnit(DeleteUnitCommand.In(id))
}
