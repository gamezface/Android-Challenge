package com.gamezface.data.remote.show.local.database

import androidx.room.*

@Dao
interface ShowDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(show: ShowEntity)

    @Delete
    fun removeFavorite(show: ShowEntity)

    @Query("SELECT * FROM ShowEntity")
    fun retrieveFavorites(): List<ShowEntity>?

    @Query("SELECT * FROM ShowEntity WHERE id=:id")
    fun retrieveFavorite(id: Long?): ShowEntity?
}