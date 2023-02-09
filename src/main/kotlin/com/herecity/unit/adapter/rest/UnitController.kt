package com.herecity.unit.adapter.rest


import com.herecity.common.annotation.ReqUser
import com.herecity.member.application.dto.AddMemberDto
import com.herecity.unit.adapter.dto.UnitMemberDto
import com.herecity.unit.application.dto.CreateUnitDto
import com.herecity.unit.application.dto.UnitDto
import com.herecity.unit.application.dto.UpdateUnitDto
import com.herecity.unit.application.port.input.LoadUnitUseCase
import com.herecity.unit.application.port.input.RecordUnitUseCase
import com.herecity.user.application.security.Authorize
import com.herecity.user.domain.UserDetail
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/units")
class UnitController(private val loadUnitUseCase: LoadUnitUseCase, private val recordUnitUseCase: RecordUnitUseCase) {

  @Authorize
  @Operation(summary = "유닛 목록 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\", \"USER\")")
  @GetMapping
  fun getUnits(@ReqUser user: UserDetail): List<UnitDto> = this.loadUnitUseCase.getAllUnits()

  @Authorize
  @Operation(summary = "유닛멤버 목록 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\", \"USER\")")
  @GetMapping("{id}/members")
  fun getUnitMembers(@PathVariable id: Long): List<UnitMemberDto> = this.loadUnitUseCase.getUnitMembers(id)

  @Authorize
  @Operation(summary = "유닛 등록")
  @ApiResponse(responseCode = "201")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PostMapping
  fun createUnit(@RequestBody createUnitDto: CreateUnitDto): UnitDto = this.recordUnitUseCase.createUnit(createUnitDto.name)

  @Authorize
  @Operation(summary = "유닛에 멤버 추가")
  @ApiResponse(responseCode = "201")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PostMapping("{unitId}/members/{memberId}")
  fun addMember(@PathVariable unitId: Long, @PathVariable memberId: Long): UnitMemberDto = this.recordUnitUseCase.addUnitMember(AddMemberDto(unitId, memberId))

  @Authorize
  @Operation(summary = "유닛 수정")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PatchMapping("{id}")
  fun updateUnit(@PathVariable id: Long, @RequestBody updateUnitDto: UpdateUnitDto): UnitDto = this.recordUnitUseCase.updateUnit(id, updateUnitDto.name)

  @Authorize
  @Operation(summary = "유닛 삭제")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @DeleteMapping("{id}")
  fun deleteUnit(@PathVariable id: Long) = this.recordUnitUseCase.deleteUnit(id)
}
