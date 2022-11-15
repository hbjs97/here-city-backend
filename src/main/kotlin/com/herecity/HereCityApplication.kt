package com.herecity

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing


private val logger = KotlinLogging.logger {}


@SpringBootApplication
@EnableJpaAuditing
class HereCityApplication

fun main(args: Array<String>) {
  logger.info("1")
  logger.info("2")
  runApplication<HereCityApplication>(*args)
  logger.info("3")
  logger.info("4")
}
