package com.githukudenis.newsflash.domain.interactors

import com.githukudenis.newsflash.common.NetworkResource
import com.githukudenis.newsflash.domain.model.Article
import com.githukudenis.newsflash.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNews @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(domains: List<String>): Flow<NetworkResource<List<Article>>> = flow {
        try {
            emit(NetworkResource.Loading())
            val allNews = newsRepository.getAllNews(domains)
            emit(NetworkResource.Success(allNews.articles))
        } catch (t: Throwable) {
            emit(NetworkResource.Error(t))
        }
    }

}
