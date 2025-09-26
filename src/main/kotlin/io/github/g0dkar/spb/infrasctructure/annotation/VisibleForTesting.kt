package io.github.g0dkar.spb.infrasctructure.annotation

/**
 * Indicates that the field is visible just for facilitating testing.
 */
@MustBeDocumented
@Target(AnnotationTarget.FIELD)
annotation class VisibleForTesting
