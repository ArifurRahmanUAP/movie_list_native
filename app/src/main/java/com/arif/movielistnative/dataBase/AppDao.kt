package com.arif.movielistnative.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dagger.Provides

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmark(appTable: AppTable)

    @Query("SELECT * FROM appTable")
    suspend fun getAllScheduleApps():List<AppTable>

    @Query("SELECT EXISTS(SELECT * FROM appTable WHERE id = :id) " )
    suspend fun checkIfExit(id:Int): Boolean
}