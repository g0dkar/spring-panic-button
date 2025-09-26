package io.github.g0dkar.spb

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class SpringPanicButtonApplicationTests {

	@Test
	fun contextLoads() {
	}

}
