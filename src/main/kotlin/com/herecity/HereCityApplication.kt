package com.herecity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class HereCityApplication

fun main(args: Array<String>) {
  runApplication<HereCityApplication>(*args)
}
