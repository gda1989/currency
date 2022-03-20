package com.example.pages.ui.currency_list.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core_api.entities.ECurrency
import com.example.pages.ui.currency_list.databinding.ItemCurrencyRatioBinding

class CurrencyAdapter(
    private val showTimestamps: Boolean = false,
    private val clickListener: CurrencyClickListener
) :
    RecyclerView.Adapter<CurrencyViewHolder>() {

    private val data: MutableList<ECurrency> = mutableListOf()
    private var baseRate : Double = 1.0

    //todo: diffUtil
    fun setData(newData: List<ECurrency>, baseRate : Double) {
        data.clear()
        data.addAll(newData)
        this.baseRate = baseRate
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemCurrencyRatioBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(data[position], baseRate)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}