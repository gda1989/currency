package com.example.database

import com.example.database.api.CurrencyDao

interface CurrencyDbContract {

    fun currencyDao() : CurrencyDao
}