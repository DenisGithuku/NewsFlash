package com.githukudenis.newsflash.ui.screens.articles

import androidx.lifecycle.ViewModel
import com.githukudenis.newsflash.domain.interactors.NewsInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val newsInteractors: NewsInteractors
): ViewModel() {

    private var _uiState: MutableStateFlow<ArticlesUiState> = MutableStateFlow(ArticlesUiState())
    val uiState: StateFlow<ArticlesUiState> get() = _uiState


    fun onEvent(event: ArticlesEvent) {
        when(event) {
            is ArticlesEvent.ChangeActiveScreen -> {
                _uiState.update {
                    it.copy(
                        activeScreen = event.activeScreen
                    )
                }
            }
        }
    }


}
