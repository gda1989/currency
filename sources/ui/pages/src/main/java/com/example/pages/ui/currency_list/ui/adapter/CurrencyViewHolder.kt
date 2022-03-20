package com.example.pages.ui.currency_list.ui.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.core_api.entities.ECurrency
import com.example.pages.ui.currency_list.databinding.ItemCurrencyRatioBinding
import java.math.RoundingMode

class CurrencyViewHolder(
    private val binding: ItemCurrencyRatioBinding,
    private val clickListener: CurrencyClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ECurrency, baseRate : Double) {
        binding.currencyName.text = data.getName()
        binding.currencyRate.text = ((data.getRate() / baseRate).toBigDecimal()
            .setScale(6, RoundingMode.UP).toDouble()).toString()
        if (data.isChecked()) {
            binding.isFav.setBackgroundColor(Color.BLUE)
            binding.isFav.setOnClickListener { clickListener.onDislikeClick(data) }
        } else {
            binding.isFav.setBackgroundColor(Color.WHITE)
            binding.isFav.setOnClickListener { clickListener.onLikeClick(data) }
        }
    }
}