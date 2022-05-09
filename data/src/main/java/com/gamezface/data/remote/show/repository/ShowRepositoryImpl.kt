package com.gamezface.data.remote.show.repository

import com.gamezface.data.remote.show.local.ShowLocalDataSource
import com.gamezface.data.remote.show.local.database.ShowEntity
import com.gamezface.data.remote.show.response.SearchResponse
import com.gamezface.data.remote.show.response.ShowResponse
import com.gamezface.data.remote.show.source.ShowRemoteDataSource
import com.gamezface.data.utils.getList
import com.gamezface.data.utils.getObject
import com.gamezface.domain.show.entity.Search
import com.gamezface.domain.show.entity.Show
import com.gamezface.domain.show.repository.ShowRepository
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val remoteDataSource: ShowRemoteDataSource,
    private val localDataSource: ShowLocalDataSource
) : ShowRepository {
    override suspend fun getShows(pageNumber: Int): List<Show>? =
        getList<Show, ShowResponse> { remoteDataSource.getShows(pageNumber) }

    override suspend fun searchShows(queryString: String): List<Search>? =
        getList<Search, SearchResponse> { remoteDataSource.searchShows(queryString) }

    override suspend fun searchPeople(queryString: String): List<Search>? =
        getList<Search, SearchResponse> { remoteDataSource.searchPeople(queryString) }

    override suspend fun getDetails(id: Long) =
        getObject<Show, ShowResponse> { remoteDataSource.getDetails(id) }

    override suspend fun retrieveFavorites(): List<Show>? =
        localDataSource.retrieveFavorites()?.map { it.toDomain() }

    override suspend fun retrieveFavorite(id: Long?): Show? =
        localDataSource.retrieveFavorite(id)?.toDomain()

    override suspend fun removeFavorite(show: Show) =
        localDataSource.removeFavorite(ShowEntity.toEntity(show))

    override suspend fun insertFavorite(show: Show) =
        localDataSource.insertFavorite(ShowEntity.toEntity(show))

}