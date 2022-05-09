package com.gamezface.data.remote.show.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShowEntity::class], version = 1, exportSchema = false)
abstract class ShowDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDAO
}