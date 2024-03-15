package com.prplmnstr.a1appstask.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.prplmnstr.a1appstask.data.remote.response.ApiResponse
import com.prplmnstr.a1appstask.model.Manga
import com.prplmnstr.a1appstask.repository.MangaRepository
import com.prplmnstr.a1appstask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MangaRepository
): ViewModel(){

    //state management
    var homeFragmentState : Parcelable? = null
    lateinit var detailScreenManga :Manga


    //remote data
    val mangaList = repository.getManga().cachedIn(viewModelScope)


}
