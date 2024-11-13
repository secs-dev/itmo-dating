package ru.ifmo.se.dating.api

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Suppress("MagicNumber")
data class GeneralErrorMessage(

    @get:Min(100)
    @get:Max(600)
    @Schema(
        example = "400",
        required = true,
        description = "HTTP Status Code",
    )
    @get:JsonProperty("code", required = true) val code: Int,

    @get:Pattern(regexp = "^[A-Za-z0-9 .,'-]+$")
    @get:Size(max = 64)
    @Schema(
        example = "Bad Request",
        required = true,
        description = "HTTP Status Description",
    )
    @get:JsonProperty("status", required = true) val status: String,

    @get:Pattern(regexp = "^[A-Za-z0-9 .,'-]+$")
    @get:Size(max = 128)
    @Schema(
        example = "Username must contain only latin letter",
        required = true,
        description = "Detailed Message",
    )
    @get:JsonProperty("message", required = true) val message: String
)
