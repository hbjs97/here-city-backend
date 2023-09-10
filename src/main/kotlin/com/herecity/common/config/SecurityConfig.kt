package com.herecity.common.config

import com.herecity.common.config.app.AppProperties
import com.herecity.user.application.port.outbound.UserQueryOutputPort
import com.herecity.user.application.security.JwtAuthenticationFilter
import com.herecity.user.application.security.JwtService
import com.herecity.user.application.security.TokenAccessDeniedHandler
import com.herecity.user.application.security.oauth2.CustomOAuth2UserService
import com.herecity.user.application.security.oauth2.OAuth2AuthenticationFailureHandler
import com.herecity.user.application.security.oauth2.OAuth2AuthenticationSuccessHandler
import com.herecity.user.application.security.oauth2.OAuth2AuthorizationRequestBasedOnCookieRepository
import com.herecity.user.application.security.oauth2.RestAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsUtils

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig(
    private val jwtService: JwtService,
    private val appProperties: AppProperties,
    private val authorizationRequestRepository: OAuth2AuthorizationRequestBasedOnCookieRepository,
    private val oAuth2AuthorizationRequestBasedOnCookieRepository: OAuth2AuthorizationRequestBasedOnCookieRepository,
    private val tokenAccessDeniedHandler: TokenAccessDeniedHandler,
    private val oAuth2UserService: CustomOAuth2UserService,
    private val userQueryOutputPort: UserQueryOutputPort,
) : WebSecurityConfigurerAdapter() {

    /*
     * auth 매니저 설정
     * */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager? {
        return super.authenticationManager()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    override fun configure(http: HttpSecurity) {
        http
            .httpBasic().disable()
            .csrf().disable()
            .cors().disable()
            .formLogin().usernameParameter("email").disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(RestAuthenticationEntryPoint())
            .accessDeniedHandler(tokenAccessDeniedHandler)
            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers(
                "/api/v1/auth/**",
                "/oauth2/**"
            ).permitAll()
            .and()
            .oauth2Login()
//            .clientRegistrationRepository(clientRegistrationRepository())
            .authorizationEndpoint()
            .baseUri("/oauth2/authorization")
            .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository)
            .and()
            .redirectionEndpoint()
            .baseUri("/*/oauth2/code/*")
            .and()
            .userInfoEndpoint()
            .userService(oAuth2UserService)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler())
            .failureHandler(oAuth2AuthenticationFailureHandler())

        http.addFilterBefore(JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter::class.java)
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    fun oAuth2AuthenticationSuccessHandler(): OAuth2AuthenticationSuccessHandler? {
        return OAuth2AuthenticationSuccessHandler(
            jwtService,
            appProperties,
            authorizationRequestRepository,
            userQueryOutputPort
        )
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    fun oAuth2AuthenticationFailureHandler(): OAuth2AuthenticationFailureHandler? {
        return OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository)
    }

    //    @Bean
//    private fun clientRegistrationRepository(): ClientRegistrationRepository {
//        val registrations: List<ClientRegistration> = listOf(
//            googleClientRegistration(appProperties.oauth2.googleAndroidClientId),
//            //            googleClientRegistration(appProperties.oauth2.googleIOSClientId)
//        )
//        return InMemoryClientRegistrationRepository(registrations)
//    }

//    private fun googleClientRegistration(clientId: String): ClientRegistration {
//        return ClientRegistration.withRegistrationId("google")
//            .clientId(clientId)
//            //            .clientSecret("")
//            .clientName("Google")
//            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
//            .scope("email", "profile")
//            .authorizationUri("https://accounts.google.com/o/oauth2/auth")
//            .tokenUri("https://www.googleapis.com/oauth2/v3/token")
//            .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
//            .userNameAttributeName(IdTokenClaimNames.SUB)
//            .clientName("Google")
//            .build()
//    }
}
