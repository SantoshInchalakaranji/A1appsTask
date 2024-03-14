package com.prplmnstr.a1appstask.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prplmnstr.a1appstask.data.remote.response.ApiResponse
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


    private val _mangaList = MutableLiveData<Resource<ApiResponse>>()
    val mangaList: LiveData<Resource<ApiResponse>> = _mangaList

    //state management
    var homeFragmentState : Parcelable? = null

    fun fetchManga(page: Int, nsfw: Boolean, type: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchManga(page, nsfw, type)
                _mangaList.value = response
            } catch (e: Exception) {
                _mangaList.value = Resource.Error("Error occurred: ${e.message}")
            }
        }
    }
}
