package com.example.pages.ui.currency_list.ui.adapter

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.core_api.entities.ECurrency
import com.example.pages.ui.currency_list.R
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
            binding.isFav.setImageDrawable(ContextCompat.getDrawable(binding.isFav.context, R.drawable.ic_fav_bright))
            binding.isFav.setOnClickListener { clickListener.onDislikeClick(data) }
        } else {
            binding.isFav.setImageDrawable(ContextCompat.getDrawable(binding.isFav.context, R.drawable.ic_fav_grey))
            binding.isFav.setOnClickListener { clickListener.onLikeClick(data) }
        }
    }
}