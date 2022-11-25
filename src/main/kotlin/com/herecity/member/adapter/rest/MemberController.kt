package com.herecity.member.adapter.rest

import com.herecity.common.annotation.ReqUser
import com.herecity.member.adapter.dto.MemberDto
import com.herecity.member.adapter.dto.SearchMemberDto
import com.herecity.member.application.dto.AddMemberDto
import com.herecity.member.application.dto.UpdateMemberDto
import com.herecity.member.application.port.input.LoadMemberUseCase
import com.herecity.member.application.port.input.RecordMemberUseCase
import com.herecity.user.domain.UserDetail
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
  private val loadMemberUseCase: LoadMemberUseCase,
  private val recordMemberUseCase: RecordMemberUseCase
) {
  @Operation(summary = "멤버 목록 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\", \"USER\")")
  @GetMapping
  fun getAllMembers(@ReqUser user: UserDetail, searchMemberDto: SearchMemberDto): List<MemberDto> = this.loadMemberUseCase.getMembers(searchMemberDto)

  @Operation(summary = "멤버 추가")
  @ApiResponse(responseCode = "201")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PostMapping
  fun createMember(@RequestBody addMemberDto: AddMemberDto): MemberDto = this.recordMemberUseCase.addMember(addMemberDto)

  @Operation(summary = "멤버 수정")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PatchMapping("{id}")
  fun updateMember(@PathVariable id: Long, @RequestBody updateMemberDto: UpdateMemberDto): MemberDto {
    if (updateMemberDto.unitId == null && updateMemberDto.name == null) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "멤버 수정 시 파라미터가 모두 null 일 수 없습니다.")
    }
    return this.recordMemberUseCase.updateMember(id, updateMemberDto)
  }

  @Operation(summary = "멤버 삭제")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @DeleteMapping("{id}")
  fun deleteMember(@PathVariable id: Long) = this.recordMemberUseCase.deleteMember(id)
}
