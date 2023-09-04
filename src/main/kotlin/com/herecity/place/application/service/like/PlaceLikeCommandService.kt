package com.herecity.place.application.service.like

import com.herecity.place.application.port.input.like.PlaceDisLikeCommand
import com.herecity.place.application.port.input.like.PlaceLikeCommand
import com.herecity.place.application.port.input.place.FetchPlaceQuery
import com.herecity.place.application.port.output.like.PlaceLikeCommandOutputPort
import com.herecity.place.domain.entity.PlaceLike
import org.springframework.stereotype.Service

@Service
class PlaceLikeCommandService(
    private val fetchPlaceQuery: FetchPlaceQuery,
    private val placeLikeCommandOutputPort: PlaceLikeCommandOutputPort,
) : PlaceLikeCommand, PlaceDisLikeCommand {
    override fun like(command: PlaceLikeCommand.In): PlaceLikeCommand.Out {
        fetchPlaceQuery.fetchPlace(FetchPlaceQuery.In(command.placeId))
        val placeLike = PlaceLike(command.placeId, command.userId)
        placeLike.like()
        return placeLikeCommandOutputPort.save(placeLike).let {
            PlaceLikeCommand.Out(it.liked())
        }
    }

    override fun disLike(command: PlaceDisLikeCommand.In): PlaceDisLikeCommand.Out {
        fetchPlaceQuery.fetchPlace(FetchPlaceQuery.In(command.placeId))
        val placeLike = PlaceLike(command.placeId, command.userId)
        placeLike.disLike()
        return placeLikeCommandOutputPort.save(placeLike).let {
            PlaceDisLikeCommand.Out(it.liked())
        }
    }
}
