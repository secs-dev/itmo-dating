package ru.ifmo.se.dating.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import java.time.Clock

@Configuration
@EnableScheduling
class SpringConfiguration {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()
}
