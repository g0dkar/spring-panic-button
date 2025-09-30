package io.github.g0dkar.spb.infrasctructure.extensions

fun String?.isUUID(defaultValue: Boolean = false): Boolean =
    this?.matches(Regex("(?i)[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))
        ?: defaultValue

fun String?.takeIfValue(defaultValue: String, vararg values: String?): String =
    if (this != null) {
        values.firstOrNull { this == it } ?: defaultValue
    } else {
        defaultValue
    }
