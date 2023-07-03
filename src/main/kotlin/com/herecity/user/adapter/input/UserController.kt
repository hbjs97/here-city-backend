package com.herecity.user.adapter.input

import com.herecity.common.annotation.Authorize
import com.herecity.common.annotation.ReqUser
import com.herecity.user.adapter.input.request.UpdateUserProfileRequest
import com.herecity.user.adapter.input.response.UpdateUserProfileResponse
import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.input.FetchUserUseCase
import com.herecity.user.application.port.input.UpdateUserProfileCommand
import com.herecity.user.domain.vo.UserDetail
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val fetchUserUseCase: FetchUserUseCase,
    private val updateUserProfileCommand: UpdateUserProfileCommand,
) {
    @Authorize
    @Operation(summary = "프로필 조회")
    @ApiResponse(responseCode = "200", description = "프로필 조회 성공")
    @PreAuthorize("hasAnyAuthority(\"USER\",\"ADMIN\")")
    @GetMapping("profile")
    fun fetchProfile(@ReqUser user: UserDetail): UserDto = fetchUserUseCase.fetchUser(user.getId())

    @Authorize
    @Operation(summary = "프로필 수정")
    @ApiResponse(responseCode = "200", description = "프로필 수정 성공")
    @PreAuthorize("hasAnyAuthority(\"USER\",\"ADMIN\")")
    @PatchMapping(name = "profile", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateProfile(
        @ReqUser user: UserDetail,
        @ModelAttribute updateUserProfileRequest: UpdateUserProfileRequest,
    ): UpdateUserProfileResponse = updateUserProfileCommand.updateProfile(
        updateUserProfileRequest.toDomain(user.getId())
    ).let {
        UpdateUserProfileResponse(
            id = it.id,
            displayName = it.displayName,
            thumbnail = it.thumbnail,
        )
    }

}
