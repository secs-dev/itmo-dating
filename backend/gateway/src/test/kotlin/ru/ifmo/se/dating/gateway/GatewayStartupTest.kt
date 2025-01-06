package ru.ifmo.se.dating.gateway

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ActiveProfiles(profiles = ["test"])
@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    useMainMethod = SpringBootTest.UseMainMethod.ALWAYS,
)
@ContextConfiguration(initializers = [SecurityInitializer::class])
class GatewayStartupTest {
    @Test
    fun contextLoads() {
        // Okay
    }
}
