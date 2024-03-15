package com.prplmnstr.a1appstask.data.local.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prplmnstr.a1appstask.model.Favorite
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.utils.Constants

@Dao
interface FavoriteDao {


    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertToFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorites(favorites: List<Favorite>)


    @Query("SELECT * FROM ${Constants.FAVORITE_TABLE}")
    fun getAllFavorites(): LiveData<List<Favorite>>
}