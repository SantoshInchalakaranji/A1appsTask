package com.prplmnstr.a1appstask.data.local.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.utils.Constants

@Dao
interface MangaDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertAllManga(mangas: List<Manga>)

    @Query("DELETE FROM ${Constants.MANGA_TABLE}")
    suspend fun deleteAllManga()

    @Query("SELECT * FROM ${Constants.MANGA_TABLE}")
    fun getAllManga(): PagingSource<Int, Manga>
}