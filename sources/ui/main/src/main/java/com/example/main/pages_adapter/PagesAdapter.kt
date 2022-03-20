package com.example.main.pages_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.core_api.mediators.CurrencyListMediator
import com.example.sorting_utils.SortingMode
import kotlinx.coroutines.flow.StateFlow

class PagesAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val currencyListMediator: CurrencyListMediator
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> currencyListMediator.createCurrenciesListFragment()
            else -> currencyListMediator.createFavsListFragment()
        }
    }
}