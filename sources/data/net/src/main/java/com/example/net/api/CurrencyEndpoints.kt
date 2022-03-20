package com.example.net.api

import com.example.net.dto.GeneralRatesListDTO
import retrofit2.http.GET

interface CurrencyEndpoints {

    /**
     * получает список всех валют
     */
    @GET("/latest")
    suspend fun getLatestCurrencies(): GeneralRatesListDTO

//    GET /latest?base=USD
//
//    Request specific exchange rates by setting the symbols parameter.
//
//    GET /latest?symbols=USD,GBP

}