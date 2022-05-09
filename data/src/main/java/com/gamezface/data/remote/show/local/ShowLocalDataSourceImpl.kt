package com.gamezface.data.remote.show.local

import com.gamezface.data.remote.show.local.database.ShowDatabase
import com.gamezface.data.remote.show.local.database.ShowEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowLocalDataSourceImpl @Inject constructor(
    private val showDatabase: ShowDatabase
) : ShowLocalDataSource {
    private val movieDao by lazy { showDatabase.showDao() }

    override suspend fun insertFavorite(show: ShowEntity) = withContext(Dispatchers.IO) {
        movieDao.insertFavorite(show)
    }

    override suspend fun removeFavorite(show: ShowEntity) = withContext(Dispatchers.IO) {
        movieDao.removeFavorite(show)
    }

    override suspend fun retrieveFavorite(id: Long?) = withContext(Dispatchers.IO) {
        movieDao.retrieveFavorite(id)
    }

    override suspend fun retrieveFavorites() = withContext(Dispatchers.IO) {
        movieDao.retrieveFavorites()
    }
}