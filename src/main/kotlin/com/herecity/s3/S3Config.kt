package com.herecity.s3

data class S3Config(
    var accessKeyId: String,
    var secretAccessKey: String,
    var domain: String,
    var bucketName: String,
    var region: String,
)
