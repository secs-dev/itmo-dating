package ru.ifmo.se.dating.matchmaker;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest(
    classes = {OpenApiGeneratorApplication.class},
    useMainMethod = SpringBootTest.UseMainMethod.ALWAYS
)
abstract class MatchmakerTestSuite {    

}
