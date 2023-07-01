package com.herecity.s3.core

import org.springframework.web.multipart.MultipartFile

data class UploadResult(
    val file: MultipartFile,
    val bucketName: String,
    val key: String,
)
