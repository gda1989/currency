package com.example.database.api

import androidx.room.*
import com.example.database.dto.EDbCurrency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM RATES")
     fun getCurrencies(): Flow<List<EDbCurrency>>

    @Insert
     fun saveCurrency(item: EDbCurrency)

    @Update
     fun update(vararg items: EDbCurrency)

    @Delete
     fun delete(item: EDbCurrency)
}