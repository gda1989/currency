package com.example.core_api.mediators

interface MediatorsProvider {

    fun provideCurrencyListMediator(): CurrencyListMediator

    fun provideMainMediator(): SortingPageMediator
}