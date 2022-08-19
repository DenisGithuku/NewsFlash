package com.githukudenis.newsflash.domain.repository

import com.githukudenis.newsflash.domain.model.ArticleDTO
import com.githukudenis.newsflash.domain.model.TopHeadlinesSourcesDTO

interface NewsRepository {

    suspend fun getAllHeadlines(source: String): ArticleDTO

    suspend fun getTopHeadlineSources(): TopHeadlinesSourcesDTO

    suspend fun getAllNews(domains: List<String>): ArticleDTO
}
