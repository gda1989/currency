package com.example.core_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.CurrencyDbContract
import com.example.database.dto.DateConverter
import com.example.database.dto.EDbCurrency

@Database(entities = [EDbCurrency::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class CurrencyDatabase : RoomDatabase(), CurrencyDbContract