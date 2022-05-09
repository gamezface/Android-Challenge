package com.gamezface.core.modules

import android.content.Context
import androidx.room.Room
import com.gamezface.data.encrypt.EncryptSharedPreferencesImpl
import com.gamezface.data.remote.show.local.ShowLocalDataSource
import com.gamezface.data.remote.show.local.ShowLocalDataSourceImpl
import com.gamezface.data.remote.show.local.database.ShowDatabase
import com.gamezface.domain.pin.EncryptSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun provideMovieLocalDataSource(database: ShowDatabase): ShowLocalDataSource {
        return ShowLocalDataSourceImpl(database)
    }

    @Provides
    fun providesAppDb(@ApplicationContext context: Context): ShowDatabase {
        return Room.databaseBuilder(
            context,
            ShowDatabase::class.java, "database-shows"
        ).build()
    }

    @Singleton
    @Provides
    fun providesEncryptedSharedPreferences(@ApplicationContext context: Context): EncryptSharedPreferences {
        return EncryptSharedPreferencesImpl(context)
    }
}