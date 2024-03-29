package com.githukudenis.newsflash.domain.interactors

import com.githukudenis.newsflash.common.NetworkResource
import com.githukudenis.newsflash.domain.model.TopHeadlinesSourcesDTO
import com.githukudenis.newsflash.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopHeadlineSources @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<NetworkResource<TopHeadlinesSourcesDTO>> = flow {
        try {
            emit(NetworkResource.Loading())
            val sources = newsRepository.getTopHeadlineSources()
            emit(NetworkResource.Success(sources))
        } catch (e: Throwable) {
            emit(NetworkResource.Error(error = e))
        }
    }
}
