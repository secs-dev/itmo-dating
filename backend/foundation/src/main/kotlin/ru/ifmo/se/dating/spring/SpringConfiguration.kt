package ru.ifmo.se.dating.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class SpringConfiguration {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()
}
