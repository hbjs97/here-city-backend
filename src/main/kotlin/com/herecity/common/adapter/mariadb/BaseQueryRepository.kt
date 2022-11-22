package com.herecity.common.adapter.mariadb

interface BaseQueryRepository<T, ID> {
  fun getById(id: ID): T
  fun findById(id: ID): T?
}
