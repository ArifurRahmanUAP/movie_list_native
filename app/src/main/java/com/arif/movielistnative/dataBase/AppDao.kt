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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGenres(genresTable: GenresTable)

    @Query("SELECT * FROM appTable")
    suspend fun getAllBookmarks():List<AppTable>

    @Query("SELECT EXISTS(SELECT * FROM appTable WHERE id = :id) " )
    suspend fun checkIfExit(id:Int): Boolean
    @Query("Delete FROM appTable WHERE id = :id " )
    suspend fun deleteBookmark(id: Int?)

}