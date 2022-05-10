package com.gamezface.presentation.viewmodels.pin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gamezface.domain.pin.EncryptSharedPreferences
import com.gamezface.domain.pin.NumPadListener
import com.gamezface.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val sharedPreferences: EncryptSharedPreferences
) : ViewModel() {

    private val _registeredPinLiveEvent = SingleLiveEvent<String>()
    fun getRegisteredPin(): LiveData<String> = _registeredPinLiveEvent

    private val pinCode = MutableLiveData<String>()
    fun getPin(): LiveData<String> = pinCode

    private val _authenticated = SingleLiveEvent<Boolean>()
    fun getAuthentication(): LiveData<Boolean> = _authenticated

    fun verifyPinRegistered() {
        _registeredPinLiveEvent.postValue(sharedPreferences.getSharedPreferences().getString(PIN_CODE_KEY, ""))
    }

    val numPadListener = object : NumPadListener {
        override fun onNumberClicked(number: Char) {
            pinCode.value.orEmpty().takeIf { it.length < 4 }?.run {
                val newValue = this + number
                pinCode.postValue(newValue)
                if (newValue.length == 4) handlePin(newValue)
            }
        }

        override fun onEraseClicked() {
            val droppedLast = pinCode.value?.dropLast(1) ?: ""
            pinCode.postValue(droppedLast)
        }
    }

    private fun handlePin(newValue: String) {
        sharedPreferences.getSharedPreferences().getString(PIN_CODE_KEY, "")
            .takeUnless { it.isNullOrEmpty() }?.run {
            _authenticated.postValue(this == newValue)
        } ?: run {
            sharedPreferences.getSharedPreferences().edit().run {
                putString(PIN_CODE_KEY, newValue)
                apply()
            }
            _authenticated.postValue(true)
        }
    }

    companion object {
        const val PIN_CODE_KEY = "PIN_CODE_KEY"
    }
}