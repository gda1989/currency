package com.example.pages.di

import androidx.fragment.app.FragmentManager
import com.example.core_api.mediators.SortingPageMediator
import com.example.pages.ui.sorting.ui.SortingPage
import javax.inject.Inject

class SortingMediatorImpl
@Inject constructor() : SortingPageMediator {

    override fun showSortingPage(fragmentBoxId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(fragmentBoxId, SortingPage.instance())
            .addToBackStack(SortingPage::class.java.simpleName)
            .commit()
    }
}