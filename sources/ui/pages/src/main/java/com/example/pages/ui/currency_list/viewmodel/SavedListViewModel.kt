package com.example.pages.ui.currency_list.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.core.DataProvidersFactory
import com.example.core.dto.Currency
import com.example.core_api.entities.ECurrency
import com.example.database.api.CurrencyDao
import com.example.database.dto.EDbCurrency
import com.example.net.repos.CurrencyRepo
import com.example.sorting_utils.SortingMode
import com.example.sorting_utils.SortingModeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

class SavedListViewModel(application: Application) : AndroidViewModel(application), ListVM {

    @Inject
    lateinit var dao: CurrencyDao

    private val repo: CurrencyRepo = DataProvidersFactory.createNetBuilder().provideCurrencyRepo()

    private val viewModelScope = CoroutineScope(Dispatchers.Default + Job())

    private val dataFlow: MutableStateFlow<List<ECurrency>> = MutableStateFlow(emptyList())

    override fun initSubscription() {
        viewModelScope.launch {
            combine(
                SortingModeProvider.sortingModeFlow,
                DataProvidersFactory.getBaseCurrencyFlow(),
                dao.getCurrencies()
            )
            {sorting,  base, fromDB ->
                val inDb = fromDB.map { it.name }
                val result = fromDB.map {
                    Currency(
                        it.name,
                        (it.rate).toBigDecimal()
                            .setScale(6, RoundingMode.UP).toDouble(),
                        it.date,
                        inDb.contains(it.name),
                        it.name == base.getName()
                    )
                }
                when(sorting){
                    SortingMode.AtoZ -> result.sortedBy { it.getName() }
                    SortingMode.ZtoA -> result.sortedByDescending { it.getName() }
                    SortingMode.BiggerFirst ->  result.sortedBy { it.getRate() }
                    SortingMode.SmallerFirst ->  result.sortedByDescending { it.getRate() }
                }
            }
                .collect {
                    dataFlow.emit(it)
                }
        }
    }

    override fun getData() {
    }

    override fun getDataFlow(): StateFlow<List<ECurrency>> = dataFlow

    override fun saveToFavorites(toSave: ECurrency) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.saveCurrency(EDbCurrency(toSave.getName(), toSave.getTimestamp(), toSave.getRate()))
        }
    }

    override fun removeFromFavorites(toRemove: ECurrency) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(EDbCurrency(toRemove.getName(), toRemove.getTimestamp(), toRemove.getRate()))
        }
    }

    override fun getBaseCurrency(): ECurrency = DataProvidersFactory.getBaseCurrencyFlow().value
}