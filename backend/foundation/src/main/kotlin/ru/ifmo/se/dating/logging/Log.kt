package ru.ifmo.se.dating.logging

interface Log {
    fun info(message: String)
    fun warn(message: String)
    fun warn(message: String, e: Throwable)
    fun error(message: String, e: Throwable)
    fun debug(message: String)

    companion object {
        inline fun <reified T : Any> T.autoLog() = forClass(T::class.java)

        fun <T> forClass(klass: Class<T>) = withName(klass.name)
        private fun withName(name: String) = Slf4jLog(name)
    }
}
