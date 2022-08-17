package com.githukudenis.newsflash.ui.screens.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.newsflash.common.NetworkResource
import com.githukudenis.newsflash.domain.interactors.NewsInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val newsInteractors: NewsInteractors,
) : ViewModel() {

    private var _uiState: MutableStateFlow<HeadlinesUiState> = MutableStateFlow(HeadlinesUiState())
    val uiState: StateFlow<HeadlinesUiState> get() = _uiState

    init {
        getTopHeadlineSources()
    }

    private var headlinesJob: Job? = null

    fun onEvent(event: HeadlinesEvent) {
        when (event) {
            is HeadlinesEvent.ChangeSource -> {
                _uiState.update {
                    it.copy(selectedSource = event.source)
                }
                getTopHeadlines(event.source.id)
            }
            is HeadlinesEvent.Search -> {

            }
        }
    }

    private fun getTopHeadlineSources() {
        headlinesJob?.cancel()
        headlinesJob = newsInteractors.getTopHeadlineSources().onEach { result ->
            when (result) {
                is NetworkResource.Error -> {
                    _uiState.update {
                        it.copy(
                            sourcesLoading = false, error = result.error.message
                        )
                    }
                }
                is NetworkResource.Loading -> {
                    _uiState.update {
                        it.copy(
                            sourcesLoading = true
                        )
                    }
                }
                is NetworkResource.Success -> {
                    _uiState.update {
                        it.copy(
                            sourcesLoading = false,
                            headlineSources = result.output_data.sources,
                            selectedSource = result.output_data.sources.first()
                        )
                    }
                    getTopHeadlines(_uiState.value.selectedSource!!.id)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopHeadlines(
        source: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            newsInteractors.getTopHeadlines(source).collect { result ->
                when (result) {
                    is NetworkResource.Error -> {
                        _uiState.update {
                            it.copy(
                                headlinesLoading = false, error = result.error.message
                            )
                        }
                    }
                    is NetworkResource.Loading -> {
                        _uiState.update {
                            it.copy(
                                headlinesLoading = true
                            )
                        }
                    }
                    is NetworkResource.Success -> {
                        _uiState.update {
                            it.copy(
                                headlinesLoading = false, headlines = result.output_data.articles
                            )
                        }
                    }
                }
            }
        }
    }
}
