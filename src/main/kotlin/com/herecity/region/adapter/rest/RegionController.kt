package com.herecity.region.adapter.rest

import com.herecity.common.annotation.ReqUser
import com.herecity.region.adapter.dto.CityDto
import com.herecity.region.adapter.dto.StreetDto
import com.herecity.region.application.dto.UpdateStreetDto
import com.herecity.region.application.port.input.LoadRegionUseCase
import com.herecity.region.application.port.input.RecordRegionUseCase
import com.herecity.user.domain.UserDetail
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/regions")
class RegionController(
  private val loadRegionUseCase: LoadRegionUseCase,
  private val recordRegionUseCase: RecordRegionUseCase
) {
  @Operation(summary = "도시 목록 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\", \"USER\")")
  @GetMapping("cities")
  fun getAllCities(@ReqUser user: UserDetail): List<CityDto> = this.loadRegionUseCase.getCities()

  @Operation(summary = "도시 거리 목록 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\", \"USER\")")
  @GetMapping("cities/{id}/streets")
  fun getCityStreets(@ReqUser user: UserDetail, @PathVariable id: Long): List<StreetDto> = this.loadRegionUseCase.getStreets(id)


  @Operation(summary = "거리 목록 조회")
  @ApiResponse(responseCode = "200")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\", \"USER\")")
  @GetMapping("streets")
  fun getStreets(@ReqUser user: UserDetail): List<StreetDto> = this.loadRegionUseCase.getStreets(null)

  @Operation(summary = "도시 등록")
  @ApiResponse(responseCode = "201")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PostMapping("cities")
  fun createCity(@RequestParam name: String): CityDto = this.recordRegionUseCase.createCity(name)

  @Operation(summary = "거리 추가")
  @ApiResponse(responseCode = "201")
  @ResponseStatus(value = HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PostMapping("cities/{id}/streets")
  fun addStreet(@PathVariable id: Long, @RequestParam name: String): StreetDto = this.recordRegionUseCase.addStreet(id, name)

  @Operation(summary = "도시 이름 수정")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PatchMapping("cities/{id}")
  fun updateCity(@PathVariable id: Long, @RequestParam name: String): CityDto = this.recordRegionUseCase.updateCityName(id, name)

  @Operation(summary = "거리 수정")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @PatchMapping("streets/{id}")
  fun updateStreet(@RequestBody updateStreetDto: UpdateStreetDto, @PathVariable id: Long): StreetDto {
    if (updateStreetDto.cityId == null && updateStreetDto.name == null) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "거리 수정 시 파라미터가 모두 null 일 수 없습니다.")
    }
    return this.recordRegionUseCase.updateStreet(id, updateStreetDto)
  }

  @Operation(summary = "도시 삭제", description = "해당 도시에 속한 거리가 존재하면 삭제할 수 없습니다.")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @DeleteMapping("cities/{id}")
  fun deleteCity(@PathVariable id: Long) = this.recordRegionUseCase.deleteCity(id)

  @Operation(summary = "거리 삭제")
  @ApiResponse(responseCode = "202")
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  @PreAuthorize("hasAnyAuthority(\"ADMIN\")")
  @DeleteMapping("streets/{id}")
  fun deleteStreet(@PathVariable id: Long) = this.recordRegionUseCase.deleteStreet(id)
}
