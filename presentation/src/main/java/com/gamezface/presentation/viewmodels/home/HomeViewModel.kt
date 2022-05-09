package com.gamezface.presentation.viewmodels.home

import androidx.lifecycle.LiveData
import com.gamezface.domain.show.entity.Show
import com.gamezface.domain.show.repository.ShowRepository
import com.gamezface.presentation.base.BaseViewModel
import com.gamezface.presentation.base.SingleLiveEvent
import com.gamezface.presentation.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ShowRepository,
) : BaseViewModel() {
    private val _showsLiveEvent = SingleLiveEvent<ViewState<List<Show>?>>()
    fun getShows(): LiveData<ViewState<List<Show>?>> = _showsLiveEvent

    private var pageNumber = 0

    val shows = mutableListOf<Show>()

    suspend fun loadShows() {
        if (pageNumber >= 0) {
            handleWork({ repository.getShows(pageNumber) }, _showsLiveEvent)
        }
    }

    fun handleLoadShowsSuccess(data: List<Show>?) {
        val filtered = data?.filterNot { shows.contains(it) }.orEmpty()
        shows.addAll(filtered)
        ++pageNumber
    }

    fun handleEndOfPages() {
        pageNumber = -1
    }
}