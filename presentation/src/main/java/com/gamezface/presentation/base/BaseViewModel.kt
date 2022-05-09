package com.gamezface.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamezface.presentation.extensions.postError
import com.gamezface.presentation.extensions.postLoading
import com.gamezface.presentation.extensions.postSuccess
import com.gamezface.presentation.state.ViewState
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    suspend fun <T> handleWork(
        work: suspend () -> T?,
        stateLiveData: MutableLiveData<ViewState<T?>>
    ) {
        stateLiveData.postLoading()
        viewModelScope.launch {
            val result =
                runCatching { work() }
            result.onSuccess { response ->
                stateLiveData.postSuccess(response)
            }.onFailure { throwable ->
                stateLiveData.postError(throwable)
            }
        }
    }
}