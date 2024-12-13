package ru.ifmo.se.dating.matchmaker

import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClient
import ru.ifmo.se.dating.Application
import ru.ifmo.se.dating.PostgresInitializer
import ru.ifmo.se.dating.SecurityInitializer

@RunWith(SpringRunner::class)
@ActiveProfiles(profiles = ["test"])
@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    useMainMethod = SpringBootTest.UseMainMethod.ALWAYS,
)
@ContextConfiguration(
    initializers = [
        PostgresInitializer::class,
        SecurityInitializer::class,
    ],
)
abstract class MatchmakerTestSuite {
    @Autowired
    private final lateinit var webClient: WebClient.Builder

    final lateinit var self: WebClient

    @Before
    fun init() {
        self = webClient
            .baseUrl("https://localhost:8080/api/")
            .build()
    }
}
