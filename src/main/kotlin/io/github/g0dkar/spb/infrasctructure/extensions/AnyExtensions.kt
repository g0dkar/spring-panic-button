package io.github.g0dkar.spb.infrasctructure.extensions

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.jwt.Jwt

fun Any?.toHttpStatusOrDefault(default: HttpStatus): HttpStatus =
    if (this != null) {
        when (this) {
            is Number -> HttpStatus.valueOf(this.toInt())
            is String -> HttpStatus.valueOf(this.toInt())
            else -> default
        }
    } else {
        default
    }

fun Any?.getKeycloakUser(
    keycloakJwtUsernameField: String = "preferred_username",
    defaultUsername: String = "unknown",
): Pair<String, String> =
    if (this != null && this is Jwt) {
        Pair(subject, getClaimAsString(keycloakJwtUsernameField))
    } else {
        Pair("", defaultUsername)
    }

