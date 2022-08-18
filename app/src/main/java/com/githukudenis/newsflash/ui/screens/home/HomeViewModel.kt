package com.githukudenis.newsflash.ui.screens.home

import android.util.Log
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
class HomeViewModel @Inject constructor(
    private val newsInteractors: NewsInteractors,
) : ViewModel() {

    private var _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    private var headlinesJob: Job? = null
    private val errorMessages: MutableList<ErrorMessage> = mutableListOf()

    init {
        getTopHeadlineSources()
    }

    private fun getTopHeadlineSources() {
        headlinesJob?.cancel()
        headlinesJob = newsInteractors.getTopHeadlineSources().onEach { result ->
            when (result) {
                is NetworkResource.Error -> {
                    errorMessages.add(ErrorMessage(error = result.error))
                    _uiState.update {
                        it.copy(
                            sourcesLoading = false, errorMessages = errorMessages
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

    fun onEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.ChangeActiveScreen -> {
                _uiState.update {
                    it.copy(
                        activeScreen = homeEvent.activeScreen
                    )
                }
            }
            is HomeEvent.ChangeSource -> {
                _uiState.update {
                    it.copy(selectedSource = homeEvent.source)
                }
                getTopHeadlines(homeEvent.source.id)
            }
            is HomeEvent.Search -> TODO()
        }
    }

    private fun getTopHeadlines(
        source: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            newsInteractors.getTopHeadlines(source).collect { result ->
                when (result) {
                    is NetworkResource.Error -> {
                        errorMessages.add(ErrorMessage(error = result.error))
                        _uiState.update {
                            it.copy(
                                headlinesLoading = false,
                                errorMessages = errorMessages
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
