package io.github.g0dkar.spb.infrasctructure.security

/**
 * Indicates that the field holds sensitive data and must be redacted when being printed/logged.
 */
@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class SensitiveData
