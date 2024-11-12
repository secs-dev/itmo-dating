package ru.ifmo.se.dating.text

import org.apache.commons.lang3.StringUtils

fun String.abbreviated(maxLength: Int = 16): String =
    StringUtils.abbreviate(this, maxLength)
