package com.example.database

import android.content.Context
import android.content.SharedPreferences

interface AppProvider {

    fun provideContext(): Context

    fun provideSharedPrefs(): SharedPreferences
}