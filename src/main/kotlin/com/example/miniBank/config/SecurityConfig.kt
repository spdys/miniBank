package com.example.miniBank.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // disable csrf (whatever that is)
            .authorizeHttpRequests {
                it.anyRequest().permitAll()  // everything public
            }
            .formLogin { it.disable() }  // disable login UI
            .httpBasic { it.disable() }  // disable HTTP basic auth
        return http.build()
    }
}
