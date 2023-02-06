package com.arif.movielistnative.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AppTable::class, GenresTable::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAppDAo(): AppDao
}