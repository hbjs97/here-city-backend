package com.herecity.user.domain

import com.herecity.user.domain.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserSecurity(private val user: User) : UserDetails {
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf<GrantedAuthority>(
    SimpleGrantedAuthority(user.role.toString())
  )

  override fun getPassword(): String = user.password

  override fun getUsername(): String = user.email

  override fun isAccountNonExpired(): Boolean = true

  override fun isAccountNonLocked(): Boolean = true

  override fun isCredentialsNonExpired(): Boolean = true

  override fun isEnabled(): Boolean = true
}
