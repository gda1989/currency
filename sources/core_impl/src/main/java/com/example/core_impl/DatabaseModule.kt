package com.example.core_impl

import android.content.Context
import androidx.room.Room
import com.example.database.CurrencyDbContract
import com.example.database.api.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

private const val RATES_DATABASE_NAME = "RATES_DB"

@Module
class DatabaseModule {

    @Provides
    @Reusable
    fun provideCurrencyDao(contract: CurrencyDbContract) : CurrencyDao {
        return contract.currencyDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyDatabase(context: Context) : CurrencyDbContract {
        return Room.databaseBuilder(context, CurrencyDatabase::class.java, RATES_DATABASE_NAME).build()
    }
}