package com.example.net

import com.example.net.repos.CurrencyRepo

interface NetworkProvider {

    fun provideCurrencyRepo(): CurrencyRepo
}