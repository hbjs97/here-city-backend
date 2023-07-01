package dev.wimcorp.common.util

import org.slf4j.LoggerFactory

class UrlUtils {
    companion object {
        fun extractPathFromUrl(url: String): String {
            val urlRegex = "^https?://[^/]+(/.*)$".toRegex()
            val matchResult = urlRegex.find(url)
            val fullPath = matchResult?.groupValues?.getOrNull(1)
            if (fullPath == null) {
                log.error("URL에서 경로를 추출하지 못했습니다. url: $url")
                throw IllegalArgumentException("URL에서 경로를 추출하지 못했습니다. url: $url")
            }
            return fullPath.removePrefix("/")
        }

        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
