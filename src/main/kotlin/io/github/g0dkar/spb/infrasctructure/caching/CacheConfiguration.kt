package io.github.g0dkar.spb.infrasctructure.caching

import com.github.benmanes.caffeine.cache.CaffeineSpec
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfiguration {

    @Bean
    fun caffeineSpec(
        @Value($$"${spring.cache.caffeine.spec:maximumSize=50,expireAfterWrite=3s}")
        caffeineSpecString: String,
    ): CaffeineSpec =
        CaffeineSpec.parse(caffeineSpecString)

    @Bean
    fun cacheManager(caffeineSpec: CaffeineSpec): CacheManager =
        CaffeineCacheManager()
            .apply { setCaffeineSpec(caffeineSpec) }
}
