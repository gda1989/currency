package com.example.currency

import android.app.Application
import com.example.core_api.mediators.AppWithFacade
import com.example.core_api.mediators.SortingPageMediator
import com.example.core_api.mediators.ProvidersFacade
import javax.inject.Inject

class CurrencyApp : Application(), AppWithFacade {

    companion object {
        private var facadeComponent: FacadeComponent? = null
    }

    override fun getFacade(): ProvidersFacade {
        return facadeComponent ?: FacadeComponent.init(this).also {
            facadeComponent = it
        }
    }

    override fun onCreate() {
        super.onCreate()
        (getFacade() as FacadeComponent).inject(this)
        (getFacade() as FacadeComponent)
    }
}