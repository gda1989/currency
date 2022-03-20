package com.example.currency

import android.app.Application
import com.example.core.DataProvidersFactory
import com.example.core_api.mediators.CurrencyListMediator
import com.example.core_api.mediators.ProvidersFacade
import com.example.core_api.mediators.SortingPageMediator
import com.example.database.AppProvider
import com.example.database.DatabaseProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class, DatabaseProvider::class],
    modules = [MediatorsBindings::class]
)
interface FacadeComponent : ProvidersFacade {

    companion object {

        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .databaseProvider(
                    DataProvidersFactory.createDbBuilder(
                        AppComponent.create(
                            application
                        )
                    )
                )
                .build()

    }

    fun getMainPageMediator(): SortingPageMediator

    fun getCurrencyListMediator(): CurrencyListMediator

    fun inject(currencyApp: CurrencyApp)

}