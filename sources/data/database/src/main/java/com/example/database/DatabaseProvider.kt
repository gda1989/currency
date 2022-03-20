package com.example.database

import com.example.database.api.CurrencyDao

interface DatabaseProvider {

    fun provideDatabase(): CurrencyDbContract

    fun habitsDao(): CurrencyDao
}