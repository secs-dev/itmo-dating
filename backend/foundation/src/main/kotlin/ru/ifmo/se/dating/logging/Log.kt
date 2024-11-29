package ru.ifmo.se.dating.logging

interface Log {
    fun info(message: String)
    fun warn(message: String)
    fun debug(message: String)

    companion object {
        fun forClass(klass: Class<Any>) = named(klass.name)
        private fun named(name: String) = Slf4jLog(name)
    }
}
