package io.github.g0dkar.spb.infrasctructure.security

import io.github.g0dkar.spb.infrasctructure.extensions.init
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@ConditionalOnProperty("panic-button.security.enabled", havingValue = "false", matchIfMissing = true)
class DisabledSpringSecurityConfig {
    @Bean
    fun disabledSecurityConfiguration(http: HttpSecurity): SecurityFilterChain =
        http.init()
            .oauth2ResourceServer { oauth2 -> oauth2.disable() }
            .authorizeHttpRequests { auth -> auth.anyRequest().permitAll() }
            .build()
}
