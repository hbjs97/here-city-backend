package com.herecity.activity.adapter.rest

import com.herecity.activity.adapter.dto.SearchActivityDto
import com.herecity.activity.application.dto.ActivityDto
import com.herecity.activity.application.dto.CreateActivityDto
import com.herecity.activity.application.dto.UpdateActivityDto
import com.herecity.activity.application.port.input.LoadActivityUseCase
import com.herecity.activity.application.port.input.RecordActivityUseCase
import com.herecity.common.annotation.Authorize
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/activities")
class ActivityController(
    private val loadActivityUseCase: LoadActivityUseCase,
    private val recordActivityUseCase: RecordActivityUseCase,
) {
    @Operation(summary = "활동 목록 조회")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    fun getActivities(searchActivityDto: SearchActivityDto): List<ActivityDto> =
        this.loadActivityUseCase.search(searchActivityDto)

    @Authorize
    @Operation(summary = "활동 등록")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PostMapping
    fun createActivity(@RequestBody createActivityDto: CreateActivityDto): ActivityDto =
        this.recordActivityUseCase.createActivity(createActivityDto.name)

    @Authorize
    @Operation(summary = "활동 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PatchMapping("{id}")
    fun updateActivity(@PathVariable id: Long, @RequestBody updateActivityDto: UpdateActivityDto): ActivityDto =
        this.recordActivityUseCase.updateActivity(id, updateActivityDto.name)

    @Authorize
    @Operation(summary = "활동 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @DeleteMapping("{id}")
    fun deleteActivity(@PathVariable id: Long) = this.recordActivityUseCase.deleteActivity(id)
}
