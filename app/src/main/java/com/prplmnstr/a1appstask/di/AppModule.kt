package com.prplmnstr.a1appstask.di

import com.prplmnstr.a1appstask.data.remote.ApiKeyInterceptor
import com.prplmnstr.a1appstask.data.remote.MangaApi
import com.prplmnstr.a1appstask.utils.Constants
import com.prplmnstr.a1appstask.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(Constants.X_RapidAPI_Key))
            .build()
    }



    @Provides
    @Singleton
    fun provideMangaApi(okHttpClient: OkHttpClient): MangaApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(MangaApi::class.java)
    }
}