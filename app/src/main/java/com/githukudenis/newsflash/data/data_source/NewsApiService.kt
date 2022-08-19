package com.githukudenis.newsflash.data.data_source

import com.githukudenis.newsflash.BuildConfig
import com.githukudenis.newsflash.domain.model.ArticleDTO
import com.githukudenis.newsflash.domain.model.TopHeadlinesSourcesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines/sources")
    @Headers("X-Api-Key: ${BuildConfig.NEWS_API_KEY}")
    suspend fun getTopHeadlinesSources(): TopHeadlinesSourcesDTO

    @GET("top-headlines")
    @Headers("X-Api-Key: ${BuildConfig.NEWS_API_KEY}")
    suspend fun getTopHeadlines(@Query("sources") source: String): ArticleDTO

    @GET("everything")
    @Headers("X-Api-Key: ${BuildConfig.NEWS_API_KEY}")
    suspend fun getAllNews(@Query("domains") domains: List<String>): ArticleDTO
}
