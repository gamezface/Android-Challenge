package com.gamezface.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gamezface.domain.show.entity.Search
import com.gamezface.home.R
import com.gamezface.home.adapters.SearchAdapter
import com.gamezface.home.databinding.FragmentSearchBinding
import com.gamezface.home.fragments.details.ID
import com.gamezface.presentation.state.ViewState
import com.gamezface.presentation.viewmodels.home.SearchViewModel
import com.gamezface.uicommon.extensions.hide
import com.gamezface.uicommon.extensions.show
import com.gamezface.uicommon.extensions.showErrorModal
import com.gamezface.uicommon.progress.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), CoroutineScope by MainScope() {
    private lateinit var binding: FragmentSearchBinding

    private val progress by lazy { ProgressDialog(requireContext()) }

    private val searchAdapter by lazy { SearchAdapter(::onShowClick) }

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initView()
        initObservers()
    }

    private fun initListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchShows()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchViewModel.handleQueryChange(newText)
                return true
            }
        })
    }

    private fun initView() {
        with(binding) {
            recyclerViewShows.adapter = searchAdapter
        }
    }

    private fun initObservers() {
        searchViewModel.getSearchResult().observe(viewLifecycleOwner) { state ->
            handleResult(state)
        }
    }

    private fun handleResult(state: ViewState<List<Search>?>) {
        state.handleIt(
            loading = { progress.showProgress() },
            stopLoading = { progress.hideProgress() },
            success = { onSuccessState(it) },
            error = { onErrorState(it) },
        )
    }

    private fun onErrorState(throwable: Throwable?) {
        showErrorModal(throwable = throwable, action = { _, _ -> searchShows() })
    }

    private fun searchShows() {
        launch {
            searchViewModel.searchShows()
        }
    }

    private fun onSuccessState(data: List<Search>?) {
        data.takeUnless { it.isNullOrEmpty() }?.run {
            binding.includeEmptyViewLayout.root.hide()
            binding.recyclerViewShows.show()
            searchAdapter.submitList(data)
        } ?: handleEmptyView()
    }

    private fun handleEmptyView() {
        with(binding) {
            binding.includeEmptyViewLayout.root.show()
            binding.recyclerViewShows.hide()
            includeEmptyViewLayout.textViewTitle.text = getString(R.string.sorry)
            includeEmptyViewLayout.textViewDescription.text =
                getString(R.string.we_did_not_find_results_for, binding.searchView.query)
        }
    }

    private fun onShowClick(search: Search) {
        findNavController().navigate(
            R.id.action_searchFragment_to_detailsFragment,
            bundleOf(ID to search.show?.id)
        )
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}