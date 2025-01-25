package ru.ifmo.se.dating.logging

interface Log {
    suspend fun info(message: String)
    suspend fun warn(message: String)
    suspend fun warn(message: String, e: Throwable)
    suspend fun error(message: String, e: Throwable)
    suspend fun debug(message: String)

    companion object {
        inline fun <reified T : Any> T.autoLog() = forClass(T::class.java)

        fun <T> forClass(klass: Class<T>) = withName(klass.name)
        private fun withName(name: String) = Slf4jLog(name)
    }
}
