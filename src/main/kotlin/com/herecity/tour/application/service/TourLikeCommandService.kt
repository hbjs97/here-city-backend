package com.herecity.tour.application.service

import com.herecity.tour.application.port.input.TourDisLikeCommand
import com.herecity.tour.application.port.input.TourLikeCommand
import com.herecity.tour.application.port.output.TourLikeCommandOutputPort
import com.herecity.tour.application.port.output.TourOutputPort
import com.herecity.tour.domain.entity.TourLike
import org.springframework.stereotype.Service

@Service
class TourLikeCommandService(
    private val tourOutputPort: TourOutputPort,
    private val tourLikeCommandOutputPort: TourLikeCommandOutputPort,
) : TourLikeCommand, TourDisLikeCommand {
    override fun like(command: TourLikeCommand.In): TourLikeCommand.Out {
        tourOutputPort.getById(command.tourId)
        val tourLikeLike = TourLike(command.tourId, command.userId)
        tourLikeLike.like()
        return tourLikeCommandOutputPort.save(tourLikeLike).let {
            TourLikeCommand.Out(it.liked())
        }
    }

    override fun disLike(command: TourDisLikeCommand.In): TourDisLikeCommand.Out {
        tourOutputPort.getById(command.tourId)
        val tourLikeLike = TourLike(command.tourId, command.userId)
        tourLikeLike.disLike()
        return tourLikeCommandOutputPort.save(tourLikeLike).let {
            TourDisLikeCommand.Out(it.liked())
        }
    }
}
