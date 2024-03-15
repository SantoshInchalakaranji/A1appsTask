package com.prplmnstr.a1appstask.repository

import android.net.ConnectivityManager
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.prplmnstr.a1appstask.data.local.database.AppDatabase
import com.prplmnstr.a1appstask.data.local.database.MangaDao
import com.prplmnstr.a1appstask.data.remote.MangaApi

import com.prplmnstr.a1appstask.data.remote.response.ApiResponse
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.paging.MangaPagingSource
import com.prplmnstr.a1appstask.paging.MangaRemoteMediator
import com.prplmnstr.a1appstask.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


class MangaRepository @Inject constructor(
    private val api : MangaApi,
    private val mangaDao: MangaDao,
    private val appDatabase: AppDatabase,
    private val connectivityManager: ConnectivityManager
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getManga() = Pager(
        config = PagingConfig(pageSize = 25, maxSize = 100),
        remoteMediator = MangaRemoteMediator(api,appDatabase),
        pagingSourceFactory = {appDatabase.mangaDao().getAllManga()}
    )
    .liveData




}