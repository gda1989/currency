package com.example.net

import com.example.net.repos.CurrencyRepo
import dagger.Component

@Component(modules = [NetworkModule::class])
interface NetworkComponent : NetworkProvider {

    companion object {

        private var networkComponent: NetworkProvider? = null

        fun create(): NetworkProvider {
            return networkComponent ?: DaggerNetworkComponent.builder()
                .build().also {
                    networkComponent = it
                }
        }

    }

    @Component.Builder
    interface Builder {

        fun build(): NetworkComponent
    }

    fun getCurrencyRepo() : CurrencyRepo
}