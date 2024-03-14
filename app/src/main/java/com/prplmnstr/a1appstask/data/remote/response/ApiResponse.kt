package com.prplmnstr.a1appstask.data.remote.response

import com.google.gson.annotations.SerializedName
import com.prplmnstr.a1appstask.model.Manga


data class ApiResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<Data>
)

fun ApiResponse.toMangaList(): List<Manga> {
    val mangaList = mutableListOf<Manga>()
    for (data in this.data) {
        val manga = Manga(
            id = data.id,
            authors = data.authors.joinToString(", "), // Convert List<String> to String
            createAt = data.createAt,
            genres = data.genres.joinToString(", "),
            nsfw = data.nsfw,
            status = data.status,
            subTitle = data.subTitle,
            summary = data.summary,
            thumb = data.thumb,
            title = data.title,
            totalChapter = data.totalChapter,
            type = data.type,
            updateAt = data.updateAt
        )
        mangaList.add(manga)
    }
    return mangaList
}
