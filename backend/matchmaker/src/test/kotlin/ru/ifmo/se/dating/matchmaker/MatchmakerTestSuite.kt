package ru.ifmo.se.dating.matchmaker

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
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
abstract class MatchmakerTestSuite
