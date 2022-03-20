package com.example.di

import com.example.core_api.mediators.ProvidersFacade
import com.example.main.MainActivity
import dagger.Component

@Component(
    dependencies = [ProvidersFacade::class]
)
interface MainComponent {

    companion object {

        private var mainComponent: MainComponent? = null

        fun create(providersFacade: ProvidersFacade): MainComponent {
            return mainComponent ?: DaggerMainComponent.builder()
                .providersFacade(providersFacade)
                .build()
                .also {
                    mainComponent = it
                }
        }
    }

    fun inject(mainActivity: MainActivity)
}