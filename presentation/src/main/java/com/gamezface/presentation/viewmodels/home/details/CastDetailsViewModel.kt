package com.gamezface.presentation.viewmodels.home.details

import androidx.lifecycle.LiveData
import com.gamezface.domain.cast.entity.CastDetails
import com.gamezface.domain.cast.repository.CastDetailsRepository
import com.gamezface.presentation.base.BaseViewModel
import com.gamezface.presentation.base.SingleLiveEvent
import com.gamezface.presentation.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CastDetailsViewModel @Inject constructor(
    private val repository: CastDetailsRepository
) : BaseViewModel() {

    private val _detailsLiveEvent = SingleLiveEvent<ViewState<List<CastDetails>?>>()
    fun getCastDetails(): LiveData<ViewState<List<CastDetails>?>> = _detailsLiveEvent

    var id: Long = -1L

    suspend fun loadDetails() {
        takeUnless { id == -1L }?.run {
            handleWork({ repository.getCastDetails(id) }, _detailsLiveEvent)
        }
    }
}