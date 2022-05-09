package com.gamezface.data.encrypt

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.gamezface.domain.pin.EncryptSharedPreferences
import javax.inject.Inject

class EncryptSharedPreferencesImpl @Inject constructor(context: Context): EncryptSharedPreferences {

    private val sharedPreferences: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun getSharedPreferences(): SharedPreferences {
        return sharedPreferences
    }
}