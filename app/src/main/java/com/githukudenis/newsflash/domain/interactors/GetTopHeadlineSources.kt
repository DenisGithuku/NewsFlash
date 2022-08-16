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
    suspend operator fun invoke(): Flow<NetworkResource<TopHeadlinesSourcesDTO>> = flow {
        try {
            emit(NetworkResource<TopHeadlinesSourcesDTO>().loading())
            val sources = newsRepository.getTopHeadlineSources()
            emit(NetworkResource<TopHeadlinesSourcesDTO>().success(sources))
        } catch (e: Throwable) {
            emit(NetworkResource<TopHeadlinesSourcesDTO>().error(message = e))
        }
    }
}
