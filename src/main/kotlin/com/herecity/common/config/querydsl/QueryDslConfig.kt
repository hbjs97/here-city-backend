package com.herecity.common.config.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QueryDslConfig {
  @PersistenceContext
  private val entityManager: EntityManager? = null

  @Bean
  fun jpaQueryFactory(): JPAQueryFactory = JPAQueryFactory(entityManager)
}
