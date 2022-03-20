package com.example.pages.ui.currency_list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_api.entities.ECurrency
import com.example.core_api.mediators.AppWithFacade
import com.example.pages.di.CurrencyListComponent
import com.example.pages.ui.currency_list.databinding.LayoutMainPageBinding
import com.example.pages.ui.currency_list.ui.adapter.CurrencyAdapter
import com.example.pages.ui.currency_list.ui.adapter.CurrencyClickListener
import com.example.pages.ui.currency_list.viewmodel.CurrencyListViewModel
import com.example.pages.ui.currency_list.viewmodel.ListVM
import com.example.pages.ui.currency_list.viewmodel.SavedListViewModel
import com.example.sorting_utils.SortingMode
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyListFragment : Fragment(), CurrencyClickListener {

    companion object {

        private const val MODE_KEY = "mode_key"

        fun instance(showFavs: Boolean = false) =
            CurrencyListFragment().apply {
                arguments = Bundle()
                    .apply {
                        putBoolean(MODE_KEY, showFavs)
                    }
            }
    }

    private lateinit var sortingModeFlow: StateFlow<SortingMode>

    private lateinit var binding: LayoutMainPageBinding
    private lateinit var viewModel: ListVM

    private lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component =
            CurrencyListComponent.create((requireActivity().application as AppWithFacade).getFacade())
        viewModel = if (arguments?.getBoolean(MODE_KEY) == false)
            ViewModelProvider(this).get(CurrencyListViewModel::class.java)
                .also { component.inject(it) }
        else ViewModelProvider(this).get(SavedListViewModel::class.java)
            .also { component.inject(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        CoroutineScope(Dispatchers.Main + CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }).launch { subscribe() }
        viewModel.initSubscription()
        viewModel.getData()
    }

    private fun initRecycler() {
        adapter = CurrencyAdapter(arguments?.getBoolean(MODE_KEY) ?: false, this)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private suspend fun subscribe() {
        viewModel.getDataFlow().collect {
            adapter.setData(it, viewModel.getBaseCurrency().getRate())
        }
    }

    override fun onLikeClick(eCurrency: ECurrency) {
        viewModel.saveToFavorites(eCurrency)
    }

    override fun onDislikeClick(eCurrency: ECurrency) {
        viewModel.removeFromFavorites(eCurrency)
    }
}