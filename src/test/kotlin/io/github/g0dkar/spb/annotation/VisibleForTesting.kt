package io.github.g0dkar.spb.annotation

import kotlin.annotation.AnnotationTarget.FIELD

/**
 * Indicates that the field is visible just for facilitating testing.
 */
@MustBeDocumented
@Target(FIELD)
annotation class VisibleForTesting()
