package com.herecity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class HereCityApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            val options = FirebaseOptions.builder()
//                .setCredentials(
//                    GoogleCredentials.fromStream(
//                        HereCityApplication::class.java.getResourceAsStream("/path/to/serviceAccount.json")
//                    )
//                )
//                .build()
//            FirebaseApp.initializeApp(options)
            runApplication<HereCityApplication>(*args)
        }
    }
}
