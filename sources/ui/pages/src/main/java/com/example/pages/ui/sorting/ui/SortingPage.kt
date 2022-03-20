package com.example.pages.ui.sorting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pages.ui.currency_list.databinding.LayoutSortingPageBinding
import com.example.sorting_utils.SortingMode
import com.example.sorting_utils.SortingModeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SortingPage : Fragment() {

    companion object {

        fun instance(): SortingPage = SortingPage()
    }

    private lateinit var binding: LayoutSortingPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutSortingPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        when (SortingModeProvider.sortingModeFlow.value) {
            SortingMode.AtoZ -> binding.rbAToZ.isChecked = true
            SortingMode.ZtoA -> binding.rbZToA.isChecked = true
            SortingMode.BiggerFirst -> binding.rb9To0.isChecked = true
            SortingMode.SmallerFirst -> binding.rb0To9.isChecked = true
        }
        binding.radioGroupByAlphabet.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.rbAToZ.id -> {
                    sendNewMode(SortingMode.AtoZ)
                }
                binding.rbZToA.id -> {
                    sendNewMode(SortingMode.ZtoA)
                }
            }
        }
        binding.radiogroupByRate.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.rb0To9.id -> {
                    sendNewMode(SortingMode.SmallerFirst)
                }
                binding.rb9To0.id -> {
                    sendNewMode(SortingMode.BiggerFirst)
                }
            }
        }
    }

    private fun sendNewMode(newMode: SortingMode) {
        CoroutineScope(Dispatchers.Main).launch {
            SortingModeProvider.sortingModeFlow.emit(newMode)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}