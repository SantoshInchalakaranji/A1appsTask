package com.prplmnstr.a1appstask.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prplmnstr.a1appstask.model.MangaRemoteKeys
import com.prplmnstr.a1appstask.utils.Constants

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys: List<MangaRemoteKeys>)

    @Query("DELETE FROM ${Constants.MANGA_REMOTE_KEY_TABLE}")
    suspend fun deleteAllRemoteKeys()

    @Query("SELECT * FROM ${Constants.MANGA_REMOTE_KEY_TABLE} WHERE id=:id")
    suspend fun getRemoteKeys(id: String): MangaRemoteKeys

}