package com.herecity.tour.application.dto

import com.herecity.common.converter.LocalTimeConverter
import java.time.LocalDateTime
import java.time.ZonedDateTime

data class TourThumbnailDto(
    val id: Long,
    val name: String,
    val regionName: String,
    val liked: Boolean,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val thumbnail: String?,
) {
    data class Zoned(
        val id: Long,
        val name: String,
        val regionName: String,
        val liked: Boolean,
        val from: ZonedDateTime,
        val to: ZonedDateTime,
        val thumbnail: String?,
    ) {
        companion object {
            fun from(tourThumbnailDto: TourThumbnailDto): Zoned {
                return Zoned(
                    id = tourThumbnailDto.id,
                    name = tourThumbnailDto.name,
                    regionName = tourThumbnailDto.regionName,
                    liked = tourThumbnailDto.liked,
                    from = LocalTimeConverter.convert(tourThumbnailDto.from),
                    to = LocalTimeConverter.convert(tourThumbnailDto.to),
                    thumbnail = tourThumbnailDto.thumbnail,
                )
            }
        }
    }
}
