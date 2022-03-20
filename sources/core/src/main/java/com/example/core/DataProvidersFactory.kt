package com.example.core

import com.example.core.dto.Currency
import com.example.core_api.entities.ECurrency
import com.example.core_impl.DaggerDatabaseComponent
import com.example.database.AppProvider
import com.example.database.DatabaseProvider
import com.example.net.DaggerNetworkComponent
import com.example.net.NetworkProvider
import kotlinx.coroutines.flow.MutableStateFlow

object DataProvidersFactory {

    private val loadedDataFlow = MutableStateFlow<List<ECurrency>>(emptyList())
    private val baseCurrencyFlow = MutableStateFlow<ECurrency>(Currency())

    fun getBaseCurrencyFlow() = baseCurrencyFlow
    fun getLoadedDataFlow() = loadedDataFlow

    fun createDbBuilder(appProvider: AppProvider): DatabaseProvider {
        return DaggerDatabaseComponent.builder().appProvider(appProvider).build()
    }

    fun createNetBuilder(): NetworkProvider {
        return DaggerNetworkComponent.builder().build()
    }
}