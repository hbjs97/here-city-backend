package com.herecity.user.domain

import com.herecity.user.domain.entity.User
import com.herecity.user.domain.vo.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

class UserDetail(
    private val user: User,
    private val passwordEncoder: PasswordEncoder
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf<GrantedAuthority>(
        SimpleGrantedAuthority(user.role.toString())
    )

    override fun getPassword(): String = passwordEncoder.encode("")

    override fun getUsername(): String = user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    fun getId(): UUID = user.id

    fun getEmail(): String = user.email

    fun getDisplayName(): String = user.displayName

    fun getRole(): UserRole = user.role
}
