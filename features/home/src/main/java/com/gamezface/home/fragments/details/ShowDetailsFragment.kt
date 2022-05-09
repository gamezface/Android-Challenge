package com.gamezface.home.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gamezface.domain.cast.entity.Cast
import com.gamezface.domain.show.entity.Episode
import com.gamezface.domain.show.entity.Show
import com.gamezface.home.R
import com.gamezface.home.adapters.CastAdapter
import com.gamezface.home.adapters.SeasonAdapter
import com.gamezface.home.databinding.FragmentShowDetailsBinding
import com.gamezface.presentation.viewmodels.home.details.ShowDetailsViewModel
import com.gamezface.uicommon.extensions.fromHtml
import com.gamezface.uicommon.extensions.loadImage
import com.gamezface.uicommon.extensions.show
import com.gamezface.uicommon.extensions.showErrorModal
import com.gamezface.uicommon.progress.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

const val ID = "com.gamezface.ID"

@AndroidEntryPoint
class ShowDetailsFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var binding: FragmentShowDetailsBinding

    private val progress by lazy { ProgressDialog(requireContext()) }
    private val castAdapter by lazy { CastAdapter(::onCastClick) }
    private val seasonAdapter by lazy { SeasonAdapter(::onEpisodeClick) }

    private val showDetailsViewModel: ShowDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
        initView()
        initListeners()
        initObservers()
    }

    private fun initView() {
        with(binding.includeContentInfoLayout) {
            recyclerViewCast.adapter = castAdapter
            recyclerViewSeasons.adapter = seasonAdapter
        }
    }

    private fun handleArguments() {
        arguments?.getLong(ID)?.run {
            showDetailsViewModel.id = this
            loadDetails()
        }
    }

    private fun initListeners() {
        binding.imageButtonReturn.setOnClickListener { findNavController().navigateUp() }
        binding.includeContentInfoLayout.checkBoxFavorite.setOnClickListener {
            showDetailsViewModel.handleFavoriteCheck(binding.includeContentInfoLayout.checkBoxFavorite.isChecked)
        }
    }

    private fun initObservers() {
        showDetailsViewModel.getShowDetails().observe(viewLifecycleOwner) { state ->
            state.handleIt(
                loading = { progress.showProgress() },
                stopLoading = { progress.hideProgress() },
                success = { onSuccessState(it) },
                error = { onErrorState(it) }
            )
        }
        showDetailsViewModel.getFavorite().observe(viewLifecycleOwner) {
            binding.includeContentInfoLayout.checkBoxFavorite.isChecked = it
        }
    }

    private fun onErrorState(throwable: Throwable?) {
        showErrorModal(throwable = throwable, action = { _, _ -> loadDetails() })
    }

    private fun loadDetails() {
        launch {
            showDetailsViewModel.loadDetails()
        }
    }

    private fun onSuccessState(data: Show?) {
        data?.let {
            setupViewDetails(it)
            showDetailsViewModel.verifyFavorite(it.id)
            binding.imageViewPoster.loadImage(binding.root, it.image?.getImageUrl())
        }
    }

    private fun setupViewDetails(data: Show) {
        with(binding.includeContentInfoLayout) {
            textViewTitle.text = data.name
            textViewScheduleDescription.text = data.getSchedule()
            textViewScheduleTitle.text =
                if (data.schedule?.days.isNullOrEmpty()) getString(R.string.average_runtime)
                else getString(R.string.schedule)
            textViewGenres.text = data.getGenres()
            textViewDescription.text = data.summary?.fromHtml()
            data.embedded?.episodes?.let { episodes ->
                cardViewEpisodes.show()
                seasonAdapter.submitList(episodes.groupBy { episode -> episode.season }
                    .toList())
            }
            data.embedded?.cast?.let { cast ->
                cardViewCast.show()
                castAdapter.submitList(cast)
            }
        }
    }

    private fun onEpisodeClick(episode: Episode) {
        findNavController().navigate(
            R.id.action_showDetailsFragment_to_episodeDetailsFragment, bundleOf(
                EPISODE to episode
            )
        )
    }

    private fun onCastClick(cast: Cast) {
        findNavController().navigate(
            R.id.action_showDetailsFragment_to_castDetailsFragment, bundleOf(
                PERSON to cast.person
            )
        )
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}

