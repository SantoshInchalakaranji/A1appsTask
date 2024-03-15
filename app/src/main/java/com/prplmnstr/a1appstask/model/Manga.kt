package com.prplmnstr.a1appstask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prplmnstr.a1appstask.utils.Constants.Companion.MANGA_TABLE

@Entity(tableName = MANGA_TABLE)
data class Manga(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val authors: String,
    val createAt: Long,
    val genres: String,
    val nsfw: Boolean,
    val status: String,
    val subTitle: String,
    val summary: String,
    val thumb: String,
    val title: String,
    val totalChapter: Int,
    val type: String,
    val updateAt: Long
)