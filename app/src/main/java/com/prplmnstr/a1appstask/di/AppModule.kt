package com.prplmnstr.a1appstask.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.prplmnstr.a1appstask.data.local.database.AppDatabase
import com.prplmnstr.a1appstask.data.local.database.MangaDao
import com.prplmnstr.a1appstask.data.remote.ApiKeyInterceptor
import com.prplmnstr.a1appstask.data.remote.MangaApi
import com.prplmnstr.a1appstask.utils.Constants
import com.prplmnstr.a1appstask.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE)
            .build()
    }

    @Provides
    @Singleton
    fun provideMangaDao(database: AppDatabase): MangaDao {
        return database.mangaDao()
    }
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}