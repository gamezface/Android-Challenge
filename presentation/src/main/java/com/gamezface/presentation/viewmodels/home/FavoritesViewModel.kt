package com.gamezface.presentation.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gamezface.domain.show.entity.Show
import com.gamezface.domain.show.repository.ShowRepository
import com.gamezface.presentation.base.BaseViewModel
import com.gamezface.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ShowRepository
) : BaseViewModel() {

    private val _favoritesLiveEvent = SingleLiveEvent<List<Show>?>()
    fun getFavorites(): LiveData<List<Show>?> = _favoritesLiveEvent

    private var completeList: List<Show>? = null

    fun retrieveFavorites() {
        _favoritesLiveEvent.value?.takeIf { it.isNullOrEmpty() }.run {
            viewModelScope.launch {
                val favorites = repository.retrieveFavorites()
                completeList = favorites?.sortedBy { it.name }
                _favoritesLiveEvent.postValue(completeList)
            }
        }
    }

    fun handleQueryChange(query: String) {
        _favoritesLiveEvent.postValue(completeList?.filter {
            it.name?.contains(
                query,
                true
            ) == true
        })
    }
}