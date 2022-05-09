package com.gamezface.domain.show.repository

import com.gamezface.domain.show.entity.Search
import com.gamezface.domain.show.entity.Show

interface ShowRepository {
    suspend fun getShows(pageNumber: Int): List<Show>?
    suspend fun searchShows(queryString: String): List<Search>?
    suspend fun searchPeople(queryString: String): List<Search>?
    suspend fun getDetails(id: Long): Show?
    suspend fun retrieveFavorites(): List<Show>?
    suspend fun retrieveFavorite(id: Long?): Show?
    suspend fun removeFavorite(show: Show)
    suspend fun insertFavorite(show: Show)
}