package com.api.member.global.config

import com.api.member.global.jwt.JwtAccessDeniedHandler
import com.api.member.global.jwt.JwtAuthenticationEntryPoint
import com.api.member.global.jwt.JwtConfig
import com.api.member.global.jwt.JwtTokenProvider
import com.api.member.global.userdetails.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {

        http
            .csrf().disable()
            .exceptionHandling()
                .authenticationEntryPoint(JwtAuthenticationEntryPoint())
                .accessDeniedHandler(JwtAccessDeniedHandler())
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/signUp").permitAll()
                .antMatchers("/api/v1/user/signIn").permitAll()
                .antMatchers("/api/v1/user/findPassword").permitAll()
                .antMatchers("/api/v1/phoneNumberAuthentication/**").permitAll()
                .anyRequest().authenticated()
            .and()
        .apply(JwtConfig(jwtTokenProvider))
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web
                .ignoring()
                .antMatchers(
                    "/swagger-ui/**"
                )
            web
                .ignoring()
                .mvcMatchers(
                    "/swagger-ui.html/**",
                    "/configuration/**",
                    "/swagger-resources/**",
                    "/v2/api-docs",
                    "/webjars/**",
                    "/webjars/springfox-swagger-ui/*.{js,css}"
                )
        }
    }

}
