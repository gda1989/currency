package com.example.sorting_utils

import kotlinx.coroutines.flow.MutableStateFlow

object SortingModeProvider {

    val sortingModeFlow: MutableStateFlow<SortingMode> = MutableStateFlow(SortingMode.AtoZ)
}