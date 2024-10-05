package ru.ifmo.se.dating.people

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ActiveProfiles(profiles = ["test"])
@SpringBootTest(
    classes = [OpenApiGeneratorApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    useMainMethod = SpringBootTest.UseMainMethod.ALWAYS,
)
@ContextConfiguration(
    initializers = [
        DatabaseInitializer::class,
    ],
)
abstract class PeopleTestSuite 
