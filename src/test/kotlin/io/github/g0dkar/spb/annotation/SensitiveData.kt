package io.github.g0dkar.spb.annotation

import kotlin.annotation.AnnotationTarget.FIELD

/**
 * Indicates that the field holds sensitive data and must be redacted when being printed/logged.
 */
@MustBeDocumented
@Target(FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class SensitiveData()