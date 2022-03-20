package com.example.pages.di

import com.example.core_api.mediators.ProvidersFacade
import com.example.net.NetworkModule
import com.example.pages.ui.currency_list.viewmodel.CurrencyListViewModel
import com.example.pages.ui.currency_list.viewmodel.SavedListViewModel
import dagger.Component

@Component(
    modules = [NetworkModule::class],
    dependencies = [ProvidersFacade::class]
)
interface CurrencyListComponent {

    companion object {

        private var currencyListComponent: CurrencyListComponent? = null

        fun create(providersFacade: ProvidersFacade): CurrencyListComponent {
            return currencyListComponent ?: DaggerCurrencyListComponent.builder()
                .providersFacade(providersFacade)
                .build().also {
                    currencyListComponent = it
                }
        }
    }

    fun inject(currencyListViewModel: CurrencyListViewModel)

    fun inject(savedListViewModel: SavedListViewModel)
}