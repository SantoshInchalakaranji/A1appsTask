package com.prplmnstr.a1appstask.data.remote.response

import com.google.gson.annotations.SerializedName


data class ApiResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>
)