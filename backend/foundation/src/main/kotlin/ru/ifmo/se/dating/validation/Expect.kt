package ru.ifmo.se.dating.validation

import ru.ifmo.se.dating.exception.InvalidValueException
import ru.ifmo.se.dating.text.abbreviated

fun expect(isValid: Boolean, message: StringBuilder.() -> Unit) {
    if (!isValid) {
        throw InvalidValueException(buildString(message))
    }
}

fun expect(isValid: Boolean, message: String) {
    expect(isValid) { append(message) }
}

fun expectId(number: Int) {
    expect(0 < number, "Unique id must be a positive number, got $number")
}

fun <T : Comparable<T>> expectInRange(name: String, value: T, range: ClosedRange<T>) {
    expect(value in range) {
        append("$name must be in range $range, got $value")
    }
}

fun expectMatches(name: String, text: String, regex: Regex) {
    expect(regex.matches(text)) {
        append("$name must match ${regex.pattern}, ")
        append("but got '${text.abbreviated()}'")
    }
}
