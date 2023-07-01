package com.herecity.s3.core

import org.springframework.web.multipart.MultipartFile

data class UploadObject(
    val objectKey: String,
    val file: MultipartFile,
    val bucketName: String,
    val keyPrefix: String,
)
