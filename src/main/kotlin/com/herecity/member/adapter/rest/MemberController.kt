package com.herecity.member.adapter.rest

import com.herecity.common.annotation.Authorize
import com.herecity.member.adapter.dto.MemberDto
import com.herecity.member.application.port.input.LoadMemberUseCase
import com.herecity.member.application.port.input.RecordMemberUseCase
import com.herecity.region.adapter.dto.NameDto
import com.herecity.user.domain.vo.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val loadMemberUseCase: LoadMemberUseCase,
    private val recordMemberUseCase: RecordMemberUseCase,
) {
    @Authorize
    @Operation(summary = "멤버 목록 조회")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAllRoles)
    @GetMapping
    fun getAllMembers(): List<MemberDto> = this.loadMemberUseCase.getMembers()

    @Authorize
    @Operation(summary = "멤버 생성")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PostMapping
    fun createMember(@RequestBody nameDto: NameDto): MemberDto = this.recordMemberUseCase.createMember(nameDto.name)

    @Authorize
    @Operation(summary = "멤버 수정")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @PatchMapping("{id}")
    fun updateMember(@PathVariable id: Long, @RequestBody nameDto: NameDto): MemberDto =
        this.recordMemberUseCase.updateMember(id, nameDto.name)

    @Authorize
    @Operation(summary = "멤버 삭제")
    @ApiResponse(responseCode = "200")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(UserRole.Authority.hasAdminRole)
    @DeleteMapping("{id}")
    fun deleteMember(@PathVariable id: Long) = this.recordMemberUseCase.deleteMember(id)
}
