package com.githukudenis.newsflash.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.newsflash.common.NetworkResource
import com.githukudenis.newsflash.domain.interactors.NewsInteractors
import com.githukudenis.newsflash.ui.util.ActiveScreen
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
    private var allArticlesJob: Job? = null
    private val errorMessages: MutableList<ErrorMessage> = mutableListOf()

    init {
        getTopHeadlineSources()
    }

    private fun getTopHeadlineSources() {
        headlinesJob?.cancel()
        headlinesJob = newsInteractors.getTopHeadlineSources().onEach { result ->
            errorMessages.clear()
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

                if (homeEvent.activeScreen == ActiveScreen.EVERYTHING) {
                    getAllNews()
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
                errorMessages.clear()
                when (result) {
                    is NetworkResource.Error -> {
                        errorMessages.add(ErrorMessage(error = result.error))
                        _uiState.update {
                            it.copy(
                                headlinesLoading = false, errorMessages = errorMessages
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

    private fun getAllNews() {
        allArticlesJob?.cancel()
        allArticlesJob = newsInteractors.getAllNews(_uiState.value.domains).onEach { result ->
            errorMessages.clear()
            when(result) {
                is NetworkResource.Error -> {
                    errorMessages.add(ErrorMessage(error = result.error))
                    _uiState.update {
                        it.copy(
                            errorMessages = errorMessages,
                            allArticlesLoading = false
                        )
                    }
                }
                is NetworkResource.Loading -> {
                    _uiState.update {
                        it.copy(
                            allArticlesLoading = true
                        )
                    }
                }
                is NetworkResource.Success -> {
                    _uiState.update {
                        it.copy(
                            allNews = result.data ?: emptyList(),
                            allArticlesLoading = false
                        )
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}
