package io.github.g0dkar.spb.infrasctructure.extensions

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy

fun HttpSecurity.init() =
    this
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .csrf { csrf -> csrf.disable() }
