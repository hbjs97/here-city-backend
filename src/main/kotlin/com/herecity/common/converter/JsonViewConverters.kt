package com.herecity.common.converter

interface JsonViewConverters {
    fun convert(obj: ByteArray): String
}
