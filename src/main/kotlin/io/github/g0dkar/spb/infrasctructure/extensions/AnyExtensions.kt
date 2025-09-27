package io.github.g0dkar.spb.infrasctructure.extensions

import org.springframework.http.HttpStatus

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
