package io.github.g0dkar.spb.infrasctructure.security

import io.github.g0dkar.spb.infrasctructure.extensions.init
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@ConditionalOnProperty("panic-button.security.enabled", havingValue = "true", matchIfMissing = false)
class EnabledSpringSecurityConfig {
    @Bean
    fun enabledSecurityConfiguration(
        http: HttpSecurity,
        keycloakJwtAuthnConverter: KeycloakJwtAuthnConverter,
    ): SecurityFilterChain =
        http.init()
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { it.jwtAuthenticationConverter(keycloakJwtAuthnConverter) }
            }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(GET, "/api/v1/panic").permitAll()
                auth.anyRequest().authenticated()
            }
            .build()
}
