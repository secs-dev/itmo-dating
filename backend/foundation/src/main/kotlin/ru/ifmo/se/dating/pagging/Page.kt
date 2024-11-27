package ru.ifmo.se.dating.pagging

import ru.ifmo.se.dating.validation.expect

data class Page(val offset: Int, val limit: Int) {
    init {
        expect(0 <= offset, "Offset must be non negative")
        expect(0 < limit, "Limit must be positive")
    }
}
