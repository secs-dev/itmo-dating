package ru.ifmo.se.dating.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class SpringClockConfiguration {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()
}
