package com.herecity.place.application.service.type

import com.herecity.place.application.port.input.type.FetchPlaceTypesQuery
import com.herecity.place.application.port.output.type.PlaceTypeQueryOutputPort
import org.springframework.stereotype.Service

@Service
class PlaceTypeQueryService(
    private val placeTypeQueryOutputPort: PlaceTypeQueryOutputPort,
) : FetchPlaceTypesQuery {
    override fun fetchPlaceTypes(query: FetchPlaceTypesQuery.In): FetchPlaceTypesQuery.Out {
        return FetchPlaceTypesQuery.Out(placeTypeQueryOutputPort.findOffSetPageable(query.offSetPageable))
    }
}
