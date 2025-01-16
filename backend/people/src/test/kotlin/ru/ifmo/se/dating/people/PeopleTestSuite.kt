package ru.ifmo.se.dating.people

import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.RemoveBucketArgs
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
        MinIOInitializer::class,
        SecurityInitializer::class,
    ],
)
abstract class PeopleTestSuite {
    @Value("\${storage.s3.bucket.profile-photos}")
    private final lateinit var bucket: String

    @Autowired
    private final lateinit var webClientBuilder: WebClient.Builder
    final lateinit var webClient: WebClient

    @Autowired
    private final lateinit var minio: MinioClient

    @Before
    fun before() {
        webClient = webClientBuilder
            .baseUrl("https://localhost:8080/api/")
            .build()

        MakeBucketArgs.builder()
            .bucket(bucket)
            .build()
            .let { minio.makeBucket(it) }
    }

    @After
    fun after() {
        RemoveBucketArgs.builder()
            .bucket(bucket)
            .build()
            .let { minio.removeBucket(it) }
    }
}
