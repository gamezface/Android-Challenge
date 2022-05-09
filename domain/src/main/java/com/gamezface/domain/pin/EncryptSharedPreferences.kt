package com.gamezface.domain.pin

import android.content.SharedPreferences

interface EncryptSharedPreferences {
    fun getSharedPreferences(): SharedPreferences
}