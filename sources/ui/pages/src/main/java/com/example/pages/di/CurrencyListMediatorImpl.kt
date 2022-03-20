package com.example.pages.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.core_api.mediators.CurrencyListMediator
import com.example.pages.ui.currency_list.ui.CurrencyListFragment
import javax.inject.Inject

class CurrencyListMediatorImpl
@Inject constructor() : CurrencyListMediator {

    override fun createCurrenciesListFragment(): Fragment = CurrencyListFragment.instance()

    override fun createFavsListFragment(): Fragment = CurrencyListFragment.instance(true)
}