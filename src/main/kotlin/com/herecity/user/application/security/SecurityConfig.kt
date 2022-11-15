package com.herecity.user.application.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig(private val jwtService: JwtService) : WebSecurityConfigurerAdapter() {

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

  override fun configure(http: HttpSecurity) {
    http.httpBasic().disable().csrf().disable().cors().disable()

    http.formLogin().usernameParameter("email")

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    http.authorizeRequests().antMatchers("/api/auth/login").permitAll().antMatchers("/api/**").authenticated()

    http.addFilterBefore(
      JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter::class.java
    )
  }
}
