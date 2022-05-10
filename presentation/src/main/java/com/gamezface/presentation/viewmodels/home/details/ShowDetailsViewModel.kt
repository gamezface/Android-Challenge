package com.gamezface.presentation.viewmodels.home.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gamezface.domain.show.entity.Show
import com.gamezface.domain.show.repository.ShowRepository
import com.gamezface.presentation.base.BaseViewModel
import com.gamezface.presentation.base.SingleLiveEvent
import com.gamezface.presentation.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    private val repository: ShowRepository
) : BaseViewModel() {

    private val _detailsLiveEvent = SingleLiveEvent<ViewState<Show?>>()
    fun getShowDetails(): LiveData<ViewState<Show?>> = _detailsLiveEvent

    private val _favoriteLiveEvent = SingleLiveEvent<Boolean>()
    fun getFavorite(): LiveData<Boolean> = _favoriteLiveEvent

    var id: Long = -1L

    suspend fun loadDetails() {
        takeUnless { id == -1L }?.run {
            handleWork({ repository.getDetails(id) }, _detailsLiveEvent)
        }
    }

    fun handleFavoriteCheck(isChecked: Boolean) {
        _favoriteLiveEvent.postValue(isChecked)
        if (isChecked) {
            handleFavorite { repository.insertFavorite(it) }
        } else {
            handleFavorite { repository.removeFavorite(it) }
        }
    }

    private fun handleFavorite(call: suspend (Show) -> Unit) {
        _detailsLiveEvent.value?.data?.let {
            viewModelScope.launch {
                call(it)
            }
        }
    }

    fun verifyFavorite(id: Long?) {
        viewModelScope.launch {
            val fav = repository.retrieveFavorite(id)
            _favoriteLiveEvent.postValue((fav != null))
        }
    }
}