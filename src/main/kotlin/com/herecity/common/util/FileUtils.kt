package dev.wimcorp.common.util

import org.slf4j.LoggerFactory

class FileUtils {
    companion object {
        fun extractExtension(name: String?): String {
            if (name == null) {
                log.error("파일 이름이 비어있습니다.")
                throw IllegalArgumentException("파일 이름이 비어있습니다.")
            }
            val extensionRegex = ".*\\.(.*)".toRegex()
            val matchResult = extensionRegex.find(name)
            val extension = matchResult?.groupValues?.getOrNull(1)
            if (extension == null) {
                log.error("파일 이름에서 확장자를 추출하지 못했습니다. name: $name")
                throw IllegalArgumentException("파일 이름에서 확장자를 추출하지 못했습니다. name: $name")
            }
            return extension
        }

        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
