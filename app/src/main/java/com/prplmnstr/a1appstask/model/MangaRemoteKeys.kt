package com.prplmnstr.a1appstask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prplmnstr.a1appstask.utils.Constants

@Entity(tableName = Constants.MANGA_REMOTE_KEY_TABLE)
data class MangaRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)