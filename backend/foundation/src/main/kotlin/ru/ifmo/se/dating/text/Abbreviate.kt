package ru.ifmo.se.dating.text

import org.apache.commons.lang3.StringUtils

private const val DEFAULT_ABBREVIATION_LENGTH = 16

fun String.abbreviated(maxLength: Int = DEFAULT_ABBREVIATION_LENGTH): String =
    StringUtils.abbreviate(this, maxLength)
