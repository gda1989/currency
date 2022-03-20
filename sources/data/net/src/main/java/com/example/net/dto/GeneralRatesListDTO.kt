package com.example.net.dto

import com.example.net.deserializers.DateDeserializer
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class GeneralRatesListDTO(
    val success: Boolean? = null,
    val base: String? = null,
    @JsonDeserialize(using = DateDeserializer::class)
    val date: Date? = null,
    val rates: Map<String, Double>? = null
)