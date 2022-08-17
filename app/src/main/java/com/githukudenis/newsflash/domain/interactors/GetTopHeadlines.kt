package com.githukudenis.newsflash.domain.interactors

import com.githukudenis.newsflash.common.NetworkResource
import com.githukudenis.newsflash.domain.model.ArticleDTO
import com.githukudenis.newsflash.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopHeadlines @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(source: String): Flow<NetworkResource<ArticleDTO>> = flow {
        try {
            emit(NetworkResource.Loading())
            val headlines = newsRepository.getAllHeadlines(source)
            emit(NetworkResource.Success(headlines))
        } catch (e: Throwable) {
            emit(NetworkResource.Error(e))
        }
    }
}
