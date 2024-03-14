package com.prplmnstr.a1appstask.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", apiKey)
            .addHeader("X-RapidAPI-Host", "mangaverse-api.p.rapidapi.com")
            .build()
        return chain.proceed(request)
    }
}