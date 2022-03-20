package com.example.pages.ui.currency_list.ui.adapter

import com.example.core_api.entities.ECurrency

interface CurrencyClickListener {

    fun onLikeClick(eCurrency: ECurrency)

    fun onDislikeClick(eCurrency: ECurrency)
}