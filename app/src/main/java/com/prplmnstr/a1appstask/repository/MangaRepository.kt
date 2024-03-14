package com.prplmnstr.a1appstask.repository

import android.util.Log
import com.prplmnstr.a1appstask.data.remote.MangaApi
import com.prplmnstr.a1appstask.data.remote.remoteDataSource.MangaDataSource
import com.prplmnstr.a1appstask.data.remote.response.ApiResponse
import com.prplmnstr.a1appstask.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


class MangaRepository @Inject constructor(
    private val api : MangaApi
): MangaDataSource {
    override suspend fun fetchManga(page: Int, nsfw: Boolean, type: String): Resource<ApiResponse> {
        val response = try {
            api.fetchManga(page, nsfw, type)
        } catch (e: Exception) {
            Log.e("TAG", "fetchManga: $e")
            return Resource.Error("Error Occured : $e")
        }
        return Resource.Success(response)
    }

}