package com.herecity.place.application.service

import com.herecity.place.application.dto.CreatePlaceTypeDto
import com.herecity.place.application.dto.PlaceTypeDto
import com.herecity.place.application.port.input.FetchPlaceTypeUseCase
import com.herecity.place.application.port.input.RecordPlaceTypeUseCase
import com.herecity.place.application.port.output.PlaceTypeCommandOutputPort
import com.herecity.place.application.port.output.PlaceTypeQueryOutputPort
import com.herecity.place.domain.entity.PlaceType
import com.herecity.place.domain.exception.DuplicatePlaceTypeNameException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PlaceTypeService(
    private val placeTypeQueryOutputPort: PlaceTypeQueryOutputPort,
    private val placeTypeCommandOutputPort: PlaceTypeCommandOutputPort,
) : FetchPlaceTypeUseCase, RecordPlaceTypeUseCase {

    override fun getPlaceTypes(pageable: Pageable): Page<PlaceTypeDto> {
        val pages = this.placeTypeQueryOutputPort.findByPage(pageable)
        return pages.map { v -> PlaceTypeDto(v) }
    }

    override fun createPlaceType(createPlaceTypeDto: CreatePlaceTypeDto): PlaceTypeDto {
        val exist = this.placeTypeQueryOutputPort.existsByName(createPlaceTypeDto.name)
        if (exist) throw DuplicatePlaceTypeNameException()

        val placeType = this.placeTypeCommandOutputPort.save(PlaceType(name = createPlaceTypeDto.name))
        return PlaceTypeDto(id = placeType.id, name = placeType.name)
    }

    override fun updatePlaceType(id: Long, name: String): PlaceTypeDto {
        val placeType = this.placeTypeQueryOutputPort.getById(id)
        placeType.name = name
        this.placeTypeCommandOutputPort.save(placeType)
        return PlaceTypeDto(placeType)
    }

    override fun deletePlaceType(id: Long) = this.placeTypeCommandOutputPort.deleteById(id)
}
