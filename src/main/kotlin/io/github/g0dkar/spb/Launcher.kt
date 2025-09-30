package io.github.g0dkar.spb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class Launcher

fun main(args: Array<String>) {
    runApplication<Launcher>(*args)
}
