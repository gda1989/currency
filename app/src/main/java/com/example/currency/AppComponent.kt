package com.example.currency

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.database.AppProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent : AppProvider {

    companion object {

        private const val SHARED_PREFS_NAME = "currency_sp"

        private var appComponent: AppProvider? = null

        fun create(application: Application): AppProvider {

            return appComponent ?: DaggerAppComponent
                .builder()
                .context(application.applicationContext)
                .sharedPrefs(application.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE))
                .build().also {
                    appComponent = it
                }
        }
    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun sharedPrefs(sharedPrefs : SharedPreferences) : Builder

        fun build(): AppComponent
    }
}