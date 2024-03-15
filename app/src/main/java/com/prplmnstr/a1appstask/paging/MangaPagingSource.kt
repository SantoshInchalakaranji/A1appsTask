package com.prplmnstr.a1appstask.paging

import android.net.ConnectivityManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prplmnstr.a1appstask.data.local.database.MangaDao
import com.prplmnstr.a1appstask.data.remote.MangaApi
import com.prplmnstr.a1appstask.data.remote.response.Data
import com.prplmnstr.a1appstask.data.remote.response.toMangaList
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MangaPagingSource  (
    private val mangaApi: MangaApi,
   private val mangaDao: MangaDao,
    private val connectivityManager: ConnectivityManager
):PagingSource<Int, Manga>() {
    override fun getRefreshKey(state: PagingState<Int, Manga>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Manga> {
        return try {
            val position = params.key?:1


                val response =
                    mangaApi.fetchManga(position).toMangaList()

            //val response = mangaApi.fetchManga(position)
            LoadResult.Page(
                data =  response,
                prevKey = if(position ==1 ) null else position-1,
                nextKey = if(position == Constants.MAX_PAGE_SIZE) null else position+1
            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    private fun isConnected(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}