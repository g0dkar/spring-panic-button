package io.github.g0dkar.spb.infrasctructure.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SpringSecurityConfig {
    private fun HttpSecurity.init() =
        this
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .csrf { csrf -> csrf.disable() }

    @Bean
    @ConditionalOnProperty("panic-button.security.enabled", havingValue = "false", matchIfMissing = true)
    fun disabledSecurityConfiguration(http: HttpSecurity): SecurityFilterChain =
        http.init()
            .authorizeHttpRequests { auth -> auth.anyRequest().permitAll() }
            .build()

    @Bean
    @ConditionalOnProperty("panic-button.security.enabled", havingValue = "true", matchIfMissing = false)
    fun enabledSecurityConfiguration(http: HttpSecurity): SecurityFilterChain =
        http.init()
            .oauth2ResourceServer { server -> server.jwt { } }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(GET, "/api/v1/panic").permitAll()
                auth.anyRequest().authenticated()
            }
            .build()

//    @Bean
//    fun authenticationConverter(authoritiesConverter: JwtGrantedAuthoritiesConverter): JwtAuthenticationConverter =
//        JwtAuthenticationConverter().apply {
//            authoritiesConverter.setAuthorityPrefix("spring-panic-button-backend")
//        }
//    @Bean
//    JwtAuthenticationConverter authenticationConverter(AuthoritiesConverter authoritiesConverter) {
//        var authenticationConverter = new JwtAuthenticationConverter();
//        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            return authoritiesConverter.convert(jwt.getClaims());
//        });
//        return authenticationConverter;
//    }
}
