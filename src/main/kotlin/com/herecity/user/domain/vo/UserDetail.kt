package com.herecity.user.domain.vo

import com.herecity.user.domain.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class UserDetail(
    private val user: User,
) :
//    OAuth2User,
    UserDetails {
    private var attributes: Map<String, Any> = mapOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf<GrantedAuthority>(
        SimpleGrantedAuthority(user.role.toString())
    )

    override fun getPassword(): String = ""

    override fun getUsername(): String = user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    fun getId(): UUID = user.id

    fun getEmail(): String? = user.email
    fun getDisplayName(): String = user.displayName

    fun getRole(): UserRole = user.role

//    override fun getName(): String = user.id.toString()
//
//    override fun getAttributes(): Map<String, Any> = attributes

    fun setAttributes(attributes: Map<String, Any>) {
        this.attributes = attributes
    }

    companion object {
        @JvmStatic
        fun getAnonymousUserDetail() = UserDetail(
            User(
                id = UUID.fromString("00000000-0000-0000-0000-000000000000"),
                providerId = "anonymous",
                email = "anonymous",
                password = "",
                displayName = "anonymous",
                role = UserRole.ANONYMOUS,
                provider = ProviderType.NONE,
                thumbnail = "",
            )
        )
    }
}
