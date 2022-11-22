package com.herecity.common.adapter.mariadb

interface BaseCommandRepository<T, ID> {
  fun save(entity: T): T
}
