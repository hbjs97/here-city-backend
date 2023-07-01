package com.herecity.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.herecity.s3.core.ClientProperties
import com.herecity.s3.core.PresignUseCase
import com.herecity.s3.core.UploadObject
import com.herecity.s3.core.UploadResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.slf4j.MDCContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.URL
import java.time.Instant
import java.util.Date
import kotlin.system.measureNanoTime
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@Service
class S3ClientAdapter(
    private val client: AmazonS3,
    private val methodMapper: S3MethodMapper,
    private val s3Config: S3Config,
) : PresignUseCase {
    @OptIn(ExperimentalTime::class)
    fun upload(uploadObject: UploadObject): UploadResult {
        CoroutineScope(Dispatchers.IO + MDCContext() + Job()).launch {
            log.info("[S3][UploadImage] 이미지 업로드 시작 : ${uploadObject.objectKey}")
            val elapsed = measureNanoTime {
                client.putObject(
                    uploadObject.run {
                        PutObjectRequest(
                            s3Config.bucketName,
                            objectKey,
                            file.inputStream,
                            ObjectMetadata().apply {
                                contentType = file.contentType ?: "application/octet-stream"
                                contentLength = file.size
                            }
                        )
                    }
                )
            }
            log.info("[S3][UploadImage] 이미지 업로드 완료 - $elapsed: ${uploadObject.objectKey}")
        }

        return UploadResult(
            file = uploadObject.file,
            key = uploadObject.objectKey,
            bucketName = s3Config.bucketName,
            url = "${s3Config.domain}/${uploadObject.objectKey}"
        )
    }

    @OptIn(ExperimentalTime::class)
    fun upload(obj: List<UploadObject>): List<UploadResult> {
        CoroutineScope(Dispatchers.IO + MDCContext() + Job()).launch {
            val transferManager = TransferManagerBuilder.standard()
                .withS3Client(client)
                .build()

            obj.map { v ->
                async(Dispatchers.IO) {
                    measureTimedValue {
                        transferManager.upload(
                            s3Config.bucketName,
                            v.objectKey,
                            v.file.inputStream,
                            ObjectMetadata().apply {
                                contentType = v.file.contentType ?: "application/octet-stream"
                                contentLength = v.file.size
                            }
                        ).run { Pair(v, waitForUploadResult()) }
                    }.run {
                        log.info(
                            "[S3Client][Upload] ${v.file.originalFilename}개 이미지 업로드 - ${duration.inWholeMilliseconds}ms"
                        )
                        return@async value
                    }
                }
            }.awaitAll()
            transferManager.shutdownNow(false)
//            results.map { UploadResult(file = it.first.file, key = it.second.key, bucketName = it.second.bucketName) }
        }
        return obj.map {
            UploadResult(
                file = it.file,
                bucketName = s3Config.bucketName,
                key = it.objectKey,
                url = "${s3Config.domain}/${it.objectKey}"
            )
        }
    }

    override fun generatePresignedUrl(
        objectKey: String,
        method: ClientProperties.HttpMethod,
        expireSecond: Long,
    ): URL =
        GeneratePresignedUrlRequest(s3Config.bucketName, objectKey, methodMapper.map(method)).withExpiration(
            Date.from(
                Instant.now().plusSeconds(expireSecond)
            )
        ).run {
            return client.generatePresignedUrl(this).toURI().toURL()
        }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
