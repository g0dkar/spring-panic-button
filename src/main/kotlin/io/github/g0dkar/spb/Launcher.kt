package io.github.g0dkar.spb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Launcher

fun main(args: Array<String>) {
    runApplication<Launcher>(*args)
}
