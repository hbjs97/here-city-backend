package com.herecity.common.converter

import org.springframework.stereotype.Component

@Component
class NormalConverter : JsonViewConverters {
    override fun convert(obj: ByteArray): String {
        return String(obj, Charsets.UTF_8)
    }
}
