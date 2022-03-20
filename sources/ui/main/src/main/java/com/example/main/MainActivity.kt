package com.example.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.core.DataProvidersFactory
import com.example.core.dto.Currency
import com.example.core_api.mediators.AppWithFacade
import com.example.core_api.mediators.CurrencyListMediator
import com.example.core_api.mediators.SortingPageMediator
import com.example.di.MainComponent
import com.example.main.databinding.ActivityMainBinding
import com.example.main.pages_adapter.PagesAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val BASE_CURRENCY_NAME_KEY = "base_currency_name"
        private const val BASE_CURRENCY_RATE_KEY = "base_currency_rate"
    }

    @Inject
    lateinit var sortingPageMediator: SortingPageMediator

    @Inject
    lateinit var currencyListMediator: CurrencyListMediator

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainComponent.create((application as AppWithFacade).getFacade()).inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val flow = DataProvidersFactory.getBaseCurrencyFlow()
        if (sharedPrefs.contains(BASE_CURRENCY_NAME_KEY))
            sharedPrefs.getFloat(BASE_CURRENCY_RATE_KEY, 1.0f).let {
                CoroutineScope(Dispatchers.Default).launch {
                    if (it != 1.0f)
                        flow.emit(
                            Currency(
                                name = sharedPrefs.getString(BASE_CURRENCY_NAME_KEY, "") ?: "",
                                rate = it.toDouble()
                            )
                        )
                }
            }
        initViewPager()
        subscribe()
        initSortingButton()
    }

    private fun initSortingButton() {
        binding.sortingBlock.setOnClickListener {
            sortingPageMediator.showSortingPage(binding.fragmentBox.id, supportFragmentManager)
        }
    }

    private fun initViewPager() {
        val pagesAdapter =
            PagesAdapter(supportFragmentManager, lifecycle, currencyListMediator)
        binding.pager.adapter = pagesAdapter
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.popular)
                }
                else -> {
                    tab.text = getString(R.string.prefs)
                }
            }
        }.attach()
    }

    private fun subscribe() {
        CoroutineScope(Dispatchers.Default).launch {
            DataProvidersFactory.getBaseCurrencyFlow()
                .combine(DataProvidersFactory.getLoadedDataFlow()) { eCurrency, list ->
                    if (list.isEmpty()) listOf(eCurrency)
                    else listOf(eCurrency).plus(list.filter { it.getName() != eCurrency.getName() })
                }.collect { list ->
                    withContext(Dispatchers.Main) {
                        ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_spinner_item,
                            list.map { it.getName() })
                            .also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                binding.baseCurrencyBlock.adapter = adapter
                            }
                        binding.baseCurrencyBlock.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    p0: AdapterView<*>?,
                                    p1: View?,
                                    position: Int,
                                    p3: Long
                                ) {
                                    list[position].let { item ->
                                        CoroutineScope(Dispatchers.Default).launch {
                                            DataProvidersFactory.getBaseCurrencyFlow().emit(item)
                                            sharedPrefs.edit()
                                                .putString(BASE_CURRENCY_NAME_KEY, item.getName())
                                                .putFloat(
                                                    BASE_CURRENCY_RATE_KEY, item.getRate().toFloat()
                                                ).apply()
                                        }
                                    }
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {

                                }
                            }
                    }
                }
        }
    }
}