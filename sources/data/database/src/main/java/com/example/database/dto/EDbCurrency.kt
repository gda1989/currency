package com.example.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "RATES")
data class EDbCurrency(
    @PrimaryKey val name: String,
    val date: Date,
    val rate: Double
)