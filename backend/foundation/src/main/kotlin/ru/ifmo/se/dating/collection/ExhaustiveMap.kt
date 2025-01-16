package ru.ifmo.se.dating.collection

import java.util.*
import kotlin.enums.enumEntries

interface ExhaustiveMap<K, V> : Map<K, V> {
    override operator fun get(key: K): V

    companion object {
        inline fun <reified K : Enum<K>, V> from(
            vararg pairs: Pair<K, V>,
        ): ExhaustiveMap<K, V> = from(pairs.asList())

        inline fun <reified K : Enum<K>, V> from(
            pairs: List<Pair<K, V>>,
        ): ExhaustiveMap<K, V> = object : ExhaustiveMap<K, V> {
            private val origin = pairs
                .associateTo(EnumMap(K::class.java)) { (l, r) -> l to r }

            init {
                val keys = pairs.map { it.first }
                require(keys.distinct().size == keys.size)
                require(enumEntries<K>().size == keys.size)
            }

            override val entries: Set<Map.Entry<K, V>>
                get() = origin.entries

            override val keys: Set<K>
                get() = origin.keys

            override val size: Int
                get() = origin.size

            override val values: Collection<V>
                get() = origin.values

            override operator fun get(key: K): V = origin[key]!!

            override fun isEmpty(): Boolean =
                origin.isEmpty()

            override fun containsValue(value: V): Boolean =
                origin.containsValue(value)

            override fun containsKey(key: K): Boolean =
                origin.containsKey(key)
        }

        inline fun <reified K : Enum<K>, V> from(
            mapping: (K) -> V,
        ): ExhaustiveMap<K, V> {
            val entries = enumEntries<K>()
            return Array(entries.size) {
                entries[it] to mapping(entries[it])
            }.let { from(pairs = it) }
        }
    }
}
