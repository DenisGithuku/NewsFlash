package com.githukudenis.newsflash.di

import com.githukudenis.newsflash.data.data_source.NewsApiService
import com.githukudenis.newsflash.data.repository.NewsRepositoryImpl
import com.githukudenis.newsflash.domain.interactors.GetTopHeadlineSources
import com.githukudenis.newsflash.domain.interactors.GetTopHeadlines
import com.githukudenis.newsflash.domain.interactors.NewsInteractors
import com.githukudenis.newsflash.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    @ViewModelScoped
    fun provideLoggingClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

    }

    @Provides
    @ViewModelScoped
    fun provideNewsApiService(
        okHttpClient: OkHttpClient,
    ): NewsApiService {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideNewsRepository(
        newsApiService: NewsApiService,
    ): NewsRepository {
        return NewsRepositoryImpl(newsApiService = newsApiService)
    }

    @Provides
    @ViewModelScoped
    fun provideNewsInteractors(newsRepository: NewsRepository): NewsInteractors {
        return NewsInteractors(
            getTopHeadlineSources = GetTopHeadlineSources(newsRepository = newsRepository),
            getTopHeadlines = GetTopHeadlines(newsRepository)
        )
    }

}
