package com.example.net.repos

import com.example.net.api.CurrencyEndpoints
import com.example.net.dto.GeneralRatesListDTO

/**
 * обертка для будущего маппинга. Если он вообще тут будет
 *
 * Впринципе, потом можно убрать
 */
class CurrencyRepo(private val apiService: CurrencyEndpoints) {

    suspend fun getMainCurrencyList(): GeneralRatesListDTO{
        return apiService.getLatestCurrencies()
    }
}