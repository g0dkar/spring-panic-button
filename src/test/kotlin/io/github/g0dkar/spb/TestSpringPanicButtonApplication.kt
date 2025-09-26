package io.github.g0dkar.spb

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<SpringPanicButtonApplication>().with(TestcontainersConfiguration::class).run(*args)
}
