package com.herecity.member.adapter.mariadb

import com.herecity.member.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
  fun findByName(name: String): Member?
}
