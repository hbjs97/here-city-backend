package com.herecity.tour.application.service

import com.herecity.common.converter.LocalTimeConverter
import com.herecity.place.application.port.input.place.FetchPlacesQuery
import com.herecity.place.application.port.output.review.PlaceReviewQueryOutputPort
import com.herecity.region.application.port.outbound.RegionQueryOutputPort
import com.herecity.tour.application.dto.TourPlaceDto
import com.herecity.tour.application.dto.TourThumbnailDto
import com.herecity.tour.application.port.input.FetchMyToursQuery
import com.herecity.tour.application.port.input.FetchTourPlacesReviewQuery
import com.herecity.tour.application.port.input.FetchTourPlanQuery
import com.herecity.tour.application.port.input.FetchToursQuery
import com.herecity.tour.application.port.input.ShareTourQuery
import com.herecity.tour.application.port.output.TourOutputPort
import com.herecity.user.application.port.inbound.FetchUserQuery
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class TourQueryService(
    private val tourOutputPort: TourOutputPort,
    private val regionQueryOutputPort: RegionQueryOutputPort,
    private val fetchUserQuery: FetchUserQuery,
    private val fetchPlacesQuery: FetchPlacesQuery,
    private val placeReviewQueryOutputPort: PlaceReviewQueryOutputPort,
) : ShareTourQuery, FetchTourPlanQuery, FetchToursQuery, FetchMyToursQuery, FetchTourPlacesReviewQuery {
    override fun shareJoinCode(query: ShareTourQuery.In): ShareTourQuery.Out =
        tourOutputPort.getById(query.id).let {
            ShareTourQuery.Out(
                joinCode = it.joinCode.toString(),
            )
        }

    override fun fetchTours(query: FetchToursQuery.In): FetchToursQuery.Out {
        tourOutputPort.findTours(
            offSetPageable = query.offSetPageable,
            name = query.name,
        ).let {
            return FetchToursQuery.Out(
                tours = it.content.map(TourThumbnailDto.Zoned::from),
                meta = it.meta,
            )
        }
    }

    override fun fetchTourPlan(query: FetchTourPlanQuery.In): FetchTourPlanQuery.Out {
        val tour = tourOutputPort.getById(query.id)
        val region = regionQueryOutputPort.getById(tour.regionId)
        val host = fetchUserQuery.fetchUser(FetchUserQuery.In(tour.createdBy))

        val places = fetchPlacesQuery.fetchPlaces(
            FetchPlacesQuery.In(
                ids = tour.tourPlaces.map { it.placeId }
            )
        ).places.toSet()
        if (places.size != tour.tourPlaces.size) throw NotFoundException("Invalid places")

        val tourPlaces = tour.tourPlaces.map { TourPlaceDto(it, places.first { place -> place.id == it.placeId }) }
        return FetchTourPlanQuery.Out(
            id = tour.id,
            ownerName = host.displayName,
            tourName = tour.name,
            regionName = region.name,
            scope = tour.scope.korName,
            from = tour.from,
            to = tour.to,
            tourPlaces = tourPlaces
        )
    }

    override fun fetchMyTours(query: FetchMyToursQuery.In): FetchMyToursQuery.Out {
        tourOutputPort.findMyTours(
            userId = query.userId,
            offSetPageable = query.offSetPageable,
            isPast = query.isPast,
        ).let {
            return FetchMyToursQuery.Out(
                tours = it.content.map(TourThumbnailDto.Zoned::from),
                meta = it.meta,
            )
        }
    }

    override fun fetchReviews(query: FetchTourPlacesReviewQuery.In): FetchTourPlacesReviewQuery.Out {
        val joinedTour = tourOutputPort.findJoinedTourPlaces(query.tourId, query.userId)
        val placeReviews = placeReviewQueryOutputPort.findTourPlaceReviews(tourId = joinedTour.id)
        return FetchTourPlacesReviewQuery.Out(
            name = joinedTour.name,
            from = LocalTimeConverter.convert(joinedTour.from),
            to = LocalTimeConverter.convert(joinedTour.to),
            reviews = placeReviews,
        )
    }
}
