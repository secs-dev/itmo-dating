package ru.ifmo.se.dating.storage.migration

import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import java.nio.file.Path
import javax.sql.DataSource
import kotlin.io.path.pathString

class LiquibaseMigration(
    private val changelog: Path,
    private val source: DataSource,
) : DatabaseMigration {
    override fun run() {
        val resources = ClassLoaderResourceAccessor(javaClass.classLoader)
        source.connection.use { connection ->
            val database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(JdbcConnection(connection))
            Liquibase(changelog.pathString, resources, database).use {
                it.update("development")
            }
        }
    }
}
