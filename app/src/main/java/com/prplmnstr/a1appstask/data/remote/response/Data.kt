package com.prplmnstr.a1appstask.data.remote.response


import com.google.gson.annotations.SerializedName



data class Data(
    @SerializedName("authors")
    val authors: List<String>,
    @SerializedName("create_at")
    val createAt: Long,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("nsfw")
    val nsfw: Boolean,
    @SerializedName("status")
    val status: String,
    @SerializedName("sub_title")
    val subTitle: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("total_chapter")
    val totalChapter: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("update_at")
    val updateAt: Long
)