package com.prplmnstr.a1appstask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prplmnstr.a1appstask.utils.Constants

@Entity(tableName = Constants.FAVORITE_TABLE)
data class Favorite(
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

fun Favorite.toManga(): Manga {
    return Manga(
        id = id,
        authors = authors,
        createAt = createAt,
        genres = genres,
        nsfw = nsfw,
        status = status,
        subTitle = subTitle,
        summary = summary,
        thumb = thumb,
        title = title,
        totalChapter = totalChapter,
        type = type,
        updateAt = updateAt
    )
}


