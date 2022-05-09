package com.gamezface.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gamezface.domain.show.entity.Show
import com.gamezface.home.R
import com.gamezface.home.adapters.ShowAdapter
import com.gamezface.home.databinding.FragmentHomeBinding
import com.gamezface.presentation.state.ViewState
import com.gamezface.presentation.viewmodels.home.HomeViewModel
import com.gamezface.uicommon.extensions.showErrorModal
import com.gamezface.uicommon.progress.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var binding: FragmentHomeBinding

    private val progress by lazy { ProgressDialog(requireContext()) }

    private val showAdapter by lazy { ShowAdapter(::onShowClick) }

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadItems()
        initListeners()
        initView()
        initObservers()
    }

    private fun loadItems() {
        launch {
            homeViewModel.loadShows()
        }
    }

    private fun initListeners() {
        binding.recyclerViewShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadItems()
                }
            }
        })
    }

    private fun initView() {
        binding.recyclerViewShows.adapter = showAdapter
    }

    private fun initObservers() {
        homeViewModel.getShows().observe(viewLifecycleOwner) { state ->
            handleResult(state)
        }
    }

    private fun handleResult(state: ViewState<List<Show>?>) {
        state.handleIt(
            loading = { progress.showProgress() },
            stopLoading = { progress.hideProgress() },
            success = { onSuccessState(it) },
            error = { onErrorState(it) },
        )
    }

    private fun onErrorState(throwable: Throwable?) {
        showErrorModal(throwable = throwable, action = { _, _ -> loadItems() })
    }

    private fun onSuccessState(data: List<Show>?) {
        data.takeUnless { it.isNullOrEmpty() }?.run {
            homeViewModel.handleLoadShowsSuccess(data)
            showAdapter.submitList(homeViewModel.shows.toList())
        } ?: homeViewModel.handleEndOfPages()
    }

    private fun onShowClick(show: Show) {
        findNavController().navigate(
            R.id.action_homeFragment_to_detailsFragment,
            bundleOf(ID to show.id)
        )
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }

}