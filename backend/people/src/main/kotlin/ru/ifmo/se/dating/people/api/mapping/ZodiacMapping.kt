package ru.ifmo.se.dating.people.api.mapping

import ru.ifmo.se.dating.collection.BiMap
import ru.ifmo.se.dating.collection.BiMap.Companion.invoke
import ru.ifmo.se.dating.collection.ExhaustiveMap
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.generated.ZodiacSignMessage

val zodiacMap = ExhaustiveMap.from<Person.Zodiac, ZodiacSignMessage> {
    when (it) {
        Person.Zodiac.ARIES -> ZodiacSignMessage.aries
        Person.Zodiac.TAURUS -> ZodiacSignMessage.taurus
        Person.Zodiac.GEMINI -> ZodiacSignMessage.gemini
        Person.Zodiac.CANCER -> ZodiacSignMessage.cancer
        Person.Zodiac.LEO -> ZodiacSignMessage.leo
        Person.Zodiac.VIRGO -> ZodiacSignMessage.virgo
        Person.Zodiac.LIBRA -> ZodiacSignMessage.libra
        Person.Zodiac.SCORPIO -> ZodiacSignMessage.scorpio
        Person.Zodiac.SAGITTARIUS -> ZodiacSignMessage.sagittarius
        Person.Zodiac.CAPRICORN -> ZodiacSignMessage.capricorn
        Person.Zodiac.AQUARIUS -> ZodiacSignMessage.aquarius
        Person.Zodiac.PISCES -> ZodiacSignMessage.pisces
    }
}.let { BiMap.from(it.entries.map { (k, v) -> k to v }.toList()) }

fun Person.Zodiac.toMessage(): ZodiacSignMessage = zodiacMap(this)

fun ZodiacSignMessage.toModel(): Person.Zodiac = zodiacMap(this)
