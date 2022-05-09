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
import com.gamezface.home.databinding.FragmentPersonSearchBinding
import com.gamezface.home.fragments.details.PERSON
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
class PersonSearchFragment : Fragment(), CoroutineScope by MainScope() {
    private lateinit var binding: FragmentPersonSearchBinding

    private val progress by lazy { ProgressDialog(requireContext()) }

    private val searchAdapter by lazy { SearchAdapter(::onPersonClick) }

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonSearchBinding.inflate(inflater, container, false)
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
                searchPeople()
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
            recyclerViewPerson.adapter = searchAdapter
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
        showErrorModal(throwable = throwable, action = { _, _ -> searchPeople() })
    }

    private fun searchPeople() {
        launch {
            searchViewModel.searchPeople()
        }
    }

    private fun onSuccessState(data: List<Search>?) {
        data.takeUnless { it.isNullOrEmpty() }?.run {
            binding.includeEmptyViewLayout.root.hide()
            binding.recyclerViewPerson.show()
            searchAdapter.submitList(data)
        } ?: handleEmptyView()
    }

    private fun handleEmptyView() {
        with(binding) {
            binding.includeEmptyViewLayout.root.show()
            binding.recyclerViewPerson.hide()
            includeEmptyViewLayout.textViewTitle.text = getString(R.string.sorry)
            includeEmptyViewLayout.textViewDescription.text =
                getString(R.string.we_did_not_find_results_for, binding.searchView.query)
        }
    }

    private fun onPersonClick(search: Search) {
        findNavController().navigate(
            R.id.action_personSearchFragment_to_castDetailsFragment,
            bundleOf(PERSON to search.person)
        )
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}