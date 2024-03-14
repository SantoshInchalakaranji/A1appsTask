package com.prplmnstr.a1appstask.data.remote.remoteDataSource

import com.prplmnstr.a1appstask.data.remote.response.ApiResponse
import com.prplmnstr.a1appstask.utils.Resource

interface MangaDataSource {

    suspend fun fetchManga(page: Int,nsfw: Boolean,type: String):Resource<ApiResponse>
}