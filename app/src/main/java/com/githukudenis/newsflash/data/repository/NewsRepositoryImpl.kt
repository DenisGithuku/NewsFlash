package com.githukudenis.newsflash.data.repository

import com.githukudenis.newsflash.data.data_source.NewsApiService
import com.githukudenis.newsflash.domain.model.ArticleDTO
import com.githukudenis.newsflash.domain.model.TopHeadlinesSourcesDTO
import com.githukudenis.newsflash.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService
): NewsRepository {
    override suspend fun getAllHeadlines(source: String): ArticleDTO {
        return newsApiService.getTopHeadlines(source = source)
    }

    override suspend fun getTopHeadlineSources(): TopHeadlinesSourcesDTO {
        return newsApiService.getTopHeadlinesSources()
    }

}
