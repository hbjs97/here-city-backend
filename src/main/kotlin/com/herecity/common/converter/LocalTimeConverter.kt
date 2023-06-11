package com.herecity.common.converter

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class LocalTimeConverter {
    companion object {
        fun convert(time: LocalDateTime, offset: ZoneOffset = ZoneOffset.UTC): ZonedDateTime = ZonedDateTime
            .of(time, LOCAL_ZONE_ID)
            .withZoneSameInstant(offset)

        fun convert(time: LocalDateTime?): ZonedDateTime? {
            if (time == null) {
                return null
            }
            return convert(time)
        }

        private val LOCAL_ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")
    }
}
