package ru.ifmo.se.dating.spring.storage

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.stereotype.Component
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.storage.migration.LiquibaseMigration
import kotlin.io.path.Path

@Component
class SpringLiquibaseMigration(
    @Value("\${spring.liquibase.liquibase-schema}")
    liquibaseSchema: String,

    @Value("\${spring.liquibase.change-log}")
    changelog: String,

    @Value("\${spring.datasource.url}")
    url: String,

    @Value("\${spring.datasource.username}")
    username: String,

    @Value("\${spring.datasource.password}")
    password: String,
) {
    private val log = autoLog()

    init {
        runBlocking { log.info("Running a liquibase migration...") }

        val suppressClose = false
        SingleConnectionDataSource(url, username, password, suppressClose).use {
            LiquibaseMigration(
                changelog = Path(changelog),
                serviceTablePrefix = liquibaseSchema,
                source = it
            ).run()
        }

        runBlocking { log.info("Liquibase migration was completed successfully") }
    }
}
