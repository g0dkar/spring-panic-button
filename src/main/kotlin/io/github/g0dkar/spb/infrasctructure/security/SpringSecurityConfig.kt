package io.github.g0dkar.spb.infrasctructure.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SpringSecurityConfig {
    @Bean
    @ConditionalOnProperty("panic-button.security.enabled", havingValue = "false", matchIfMissing = true)
    fun disabledSecurityConfiguration(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth -> auth.anyRequest().permitAll() }
            .build()

    @Bean
    @ConditionalOnProperty("panic-button.security.enabled", havingValue = "true", matchIfMissing = false)
    fun enabledSecurityConfiguration(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(GET, "/api/v1/panic").permitAll()
                auth.anyRequest().authenticated()
            }
            .build()
}
