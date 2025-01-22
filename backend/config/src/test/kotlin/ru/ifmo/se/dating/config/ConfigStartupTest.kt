package ru.ifmo.se.dating.config

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import ru.ifmo.se.dating.VaultInitializer

@RunWith(SpringRunner::class)
@ActiveProfiles(profiles = ["test", "vault"])
@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    useMainMethod = SpringBootTest.UseMainMethod.ALWAYS,
)
@ContextConfiguration(initializers = [VaultInitializer::class])
class ConfigStartupTest {
    @Test
    fun contextLoads() {
        // Okay
    }
}
