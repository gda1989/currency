package com.example.core_api.mediators

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface SortingPageMediator {

    fun showSortingPage(@IdRes fragmentBoxId : Int, fragmentManager : FragmentManager)
}