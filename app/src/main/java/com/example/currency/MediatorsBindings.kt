package com.example.currency

import com.example.core_api.mediators.CurrencyListMediator
import com.example.core_api.mediators.SortingPageMediator
import com.example.pages.di.CurrencyListMediatorImpl
import com.example.pages.di.SortingMediatorImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface MediatorsBindings {

    @Binds
    @Reusable
    fun bindsCurrencyListMediator(currencyListMediatorImpl: CurrencyListMediatorImpl) : CurrencyListMediator

    @Binds
    @Reusable
    fun bindsSortingPageMediator(sortingMediatorImpl: SortingMediatorImpl) : SortingPageMediator
}