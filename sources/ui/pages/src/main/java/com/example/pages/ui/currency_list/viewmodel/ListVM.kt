package com.example.pages.ui.currency_list.viewmodel

import com.example.core_api.entities.ECurrency
import kotlinx.coroutines.flow.StateFlow

interface ListVM {

    fun initSubscription()

    fun getData()

    fun getDataFlow(): StateFlow<List<ECurrency>>

    fun saveToFavorites(toSave: ECurrency)

    fun removeFromFavorites(toRemove: ECurrency)

    fun getBaseCurrency(): ECurrency
}