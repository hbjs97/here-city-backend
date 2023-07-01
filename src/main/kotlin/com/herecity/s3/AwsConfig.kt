package com.herecity.s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.herecity.common.config.app.AppProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsConfig(
    private val appProperties: AppProperties,
) {
    @Bean
    fun s3Config(): S3Config = S3Config(
        appProperties.aws.accessKeyId,
        appProperties.aws.secretAccessKey,
        appProperties.aws.s3.domain,
        appProperties.aws.s3.bucketName,
        appProperties.aws.s3.region
    )

    @Bean
    fun amazonS3(): AmazonS3 {
        val config = s3Config()
        val credentials = BasicAWSCredentials(config.accessKeyId, config.secretAccessKey)
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(config.region)
            .build()
    }
}
