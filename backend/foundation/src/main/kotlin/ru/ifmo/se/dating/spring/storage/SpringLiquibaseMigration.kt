package ru.ifmo.se.dating.spring.storage

import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.stereotype.Component
import ru.ifmo.se.dating.storage.migration.LiquibaseMigration
import kotlin.io.path.Path

@Component
class SpringLiquibaseMigration(
    @Value("\${spring.liquibase.change-log}")
    changelog: String,

    @Value("\${spring.datasource.url}")
    url: String,

    @Value("\${spring.datasource.username}")
    username: String,

    @Value("\${spring.datasource.password}")
    password: String,
) {
    init {
        val suppressClose = false
        SingleConnectionDataSource(url, username, password, suppressClose)
            .use { LiquibaseMigration(Path(changelog), it).run() }
    }
}
