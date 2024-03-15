package com.prplmnstr.a1appstask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prplmnstr.a1appstask.model.Favorite
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.model.MangaRemoteKeys

@Database(
    entities = [Manga::class,MangaRemoteKeys::class,Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mangaDao(): MangaDao
    abstract fun mangaRemoteKeysDao(): RemoteKeyDao
    abstract fun favoriteDao(): FavoriteDao


}