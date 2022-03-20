package com.example.net

import com.example.net.api.CurrencyEndpoints
import com.example.net.repos.CurrencyRepo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                var request: Request = chain.request()
                val url: HttpUrl =
                    request.url.newBuilder().addQueryParameter("access_key", BuildConfig.key)
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            })
            .build()
    }

    @Provides
    fun getRetrofit(client: OkHttpClient): Retrofit {

        val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()
        return Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BuildConfig.baseAddress)
            .client(client)
            .build()
    }

    @Provides
    fun getApiService(retrofit: Retrofit): CurrencyEndpoints {
        return retrofit.create(CurrencyEndpoints::class.java)
    }

    @Provides
    fun getCurrencyRepo(endpoints: CurrencyEndpoints) : CurrencyRepo{
        return CurrencyRepo(endpoints)
    }
}