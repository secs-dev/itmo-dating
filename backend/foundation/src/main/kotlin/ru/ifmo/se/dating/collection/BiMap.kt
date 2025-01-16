package ru.ifmo.se.dating.collection

import java.util.*
import kotlin.enums.enumEntries

interface BiMap<T, U> {
    fun toRight(value: T): U
    fun toLeft(value: U): T

    companion object {
        inline fun <reified K : Enum<K>, V> from(
            vararg pairs: Pair<K, V>,
        ): ExhaustiveMap<K, V> = ExhaustiveMap.from(pairs.asList())

        inline fun <reified T : Enum<T>, reified U : Enum<U>> from(
            pairs: List<Pair<T, U>>,
        ): BiMap<T, U> = object : BiMap<T, U> {
            private val rhs = pairs
                .associateTo(EnumMap(T::class.java)) { (l, r) -> l to r }

            private val lhs = pairs
                .associateTo(EnumMap(U::class.java)) { (l, r) -> r to l }

            init {
                val left = pairs.map { it.first }.toSet()
                val right = pairs.map { it.second }.toSet()
                require(left.size == right.size)
                require(enumEntries<T>().size == left.size)
            }

            override fun toRight(value: T): U = rhs[value]!!

            override fun toLeft(value: U): T = lhs[value]!!
        }

        @JvmName("BiMapLeftToRight")
        operator fun <T, U> BiMap<T, U>.invoke(value: T): U =
            this.toRight(value)

        @JvmName("BiMapRightToLeft")
        operator fun <T, U> BiMap<T, U>.invoke(value: U): T =
            this.toLeft(value)
    }
}
