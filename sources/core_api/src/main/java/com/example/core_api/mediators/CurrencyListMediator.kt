package com.example.core_api.mediators

import androidx.fragment.app.Fragment

interface CurrencyListMediator {

    fun createCurrenciesListFragment(): Fragment

    fun createFavsListFragment(): Fragment
}