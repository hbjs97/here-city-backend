package com.herecity.activity.adapter.inbound.web

import com.herecity.activity.adapter.inbound.web.request.CreateActivityRequest
import com.herecity.activity.adapter.inbound.web.request.SearchActivitiesRequest
import com.herecity.activity.adapter.inbound.web.request.UpdateActivityRequest
import com.herecity.activity.adapter.inbound.web.response.CreateActivityResponse
import com.herecity.activity.adapter.inbound.web.response.FetchActivitiesResponse
import com.herecity.activity.adapter.inbound.web.response.UpdateActivityResponse
import com.herecity.activity.application.port.input.CreateActivityCommand
import com.herecity.activity.application.port.input.DeleteActivityCommand
import com.herecity.activity.application.port.input.SearchActivityQuery
import com.herecity.activity.application.port.input.UpdateActivityCommand
import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.DomainContext
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/activities")
class ActivityController(
    private val searchActivityQuery: SearchActivityQuery,
    private val createActivityCommand: CreateActivityCommand,
    private val updateActivityCommand: UpdateActivityCommand,
    private val deleteActivityCommand: DeleteActivityCommand,
) {
    @Operation(summary = "활동 목록 조회")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    @DomainContext("Activity", "SearchActivities", "활동 목록을 조회(검색)한다.")
    fun searchActivities(searchActivitiesRequest: SearchActivitiesRequest): FetchActivitiesResponse =
        FetchActivitiesResponse(searchActivityQuery.search(searchActivitiesRequest.toDomain()))

    @Authorize
    @Operation(summary = "활동 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PostMapping
    @DomainContext("Activity", "CreateActivity", "새로운 활동을 생성한다.")
    fun createActivity(@RequestBody @Valid createActivityRequest: CreateActivityRequest): CreateActivityResponse =
        CreateActivityResponse.from(createActivityCommand.createActivity(createActivityRequest.toDomain()))

    @Authorize
    @Operation(summary = "활동 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PatchMapping("{id}")
    fun updateActivity(@PathVariable id: Long, @RequestBody @Valid updateActivityRequest: UpdateActivityRequest): UpdateActivityResponse =
        UpdateActivityResponse.from(updateActivityCommand.updateActivity(updateActivityRequest.toDomain(id)))

    @Authorize
    @Operation(summary = "활동 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @DeleteMapping("{id}")
    fun deleteActivity(@PathVariable id: Long) = deleteActivityCommand.deleteActivity(DeleteActivityCommand.In(id))
}
