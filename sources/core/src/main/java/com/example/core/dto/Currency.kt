package com.example.core.dto

import com.example.core_api.entities.ECurrency
import java.util.*

data class Currency(
    private val name: String = "EUR",
    private val rate: Double = 1.0,
    private val timestamp: Date = Date(0),
    private val checked: Boolean = false,
    private val isBase: Boolean = false
) : ECurrency {
    override fun getName(): String = name

    override fun getRate(): Double = rate

    override fun getTimestamp(): Date = timestamp

    override fun isChecked(): Boolean = checked
    override fun isBase(): Boolean = isBase
}
