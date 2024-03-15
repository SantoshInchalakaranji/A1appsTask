package com.prplmnstr.a1appstask.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.prplmnstr.a1appstask.data.local.database.AppDatabase
import com.prplmnstr.a1appstask.data.remote.MangaApi
import com.prplmnstr.a1appstask.data.remote.response.toMangaList
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.model.MangaRemoteKeys
import com.prplmnstr.a1appstask.utils.Constants.Companion.MAX_PAGE_SIZE

@OptIn(ExperimentalPagingApi::class)
class MangaRemoteMediator(
    private val mangaApi: MangaApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Manga>() {

    val mangaDao = appDatabase.mangaDao()
    val remoteKeyDao = appDatabase.mangaRemoteKeysDao()


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Manga>): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }


            val response = mangaApi.fetchManga(currentPage)
            val endOfPaginationReached = MAX_PAGE_SIZE == currentPage
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            appDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    mangaDao.deleteAllManga()
                    remoteKeyDao.deleteAllRemoteKeys()
                }

                mangaDao.insertAllManga(response.toMangaList())
                val keys = response.toMangaList().map { manga ->
                    MangaRemoteKeys(
                        id = manga.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteKeyDao.insertAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            Log.e("TAG", "${e.message}")
            MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Manga>
    ): MangaRemoteKeys? {
        return state.anchorPosition?.let { position ->

            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Manga>
    ): MangaRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { manga ->
                remoteKeyDao.getRemoteKeys(id = manga.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Manga>
    ): MangaRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { manga ->
                remoteKeyDao.getRemoteKeys(id = manga.id)
            }
    }
}