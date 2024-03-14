package com.prplmnstr.a1appstask.data.remote

import com.prplmnstr.a1appstask.data.remote.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MangaApi {


    @GET(value = "manga/fetch")
    suspend fun fetchManga(
        @Query("page") page: Int,
        @Query("nsfw") nsfw: Boolean,
        @Query("type") type: String

    ): ApiResponse
}