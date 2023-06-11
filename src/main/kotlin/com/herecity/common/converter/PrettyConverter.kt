package com.herecity.common.converter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.springframework.stereotype.Component

@Component
class PrettyConverter : JsonViewConverters {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    override fun convert(obj: ByteArray): String {
        return gson.toJson(JsonParser.parseString(String(obj, Charsets.UTF_8)))
    }
}
