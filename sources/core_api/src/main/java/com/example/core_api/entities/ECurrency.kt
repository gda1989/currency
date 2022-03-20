package com.example.core_api.entities

import java.util.*

/**
 * описывает модель курса валюты для вью-слоя
 */
interface ECurrency {

    fun getName(): String
    fun getRate(): Double
    fun getTimestamp(): Date

    fun isChecked(): Boolean
    fun isBase(): Boolean
}