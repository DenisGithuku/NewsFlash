package com.githukudenis.newsflash.ui.screens.home

import com.githukudenis.newsflash.domain.model.Article
import com.githukudenis.newsflash.domain.model.SourceX
import com.githukudenis.newsflash.ui.util.ActiveScreen

data class HomeUiState (
    val activeScreen: ActiveScreen = ActiveScreen.HEADLINES,
    val availableScreens: List<ActiveScreen> = listOf(ActiveScreen.HEADLINES, ActiveScreen.EVERYTHING),
    val sourcesLoading: Boolean = false,
    val headlinesLoading: Boolean = false,
    val headlineSources: List<SourceX> = emptyList(),
    val selectedSource: SourceX? = null,
    var searchQuery: String = "",
    val headlines: List<Article> = emptyList(),
    val errorMessages: MutableList<ErrorMessage> = mutableListOf()
) {
    fun HomeUiState.addErrorMessage(errorMessage: ErrorMessage) = errorMessages.add(errorMessage)
}

data class ErrorMessage(
    val error: Throwable? = null,
    val cause: String? = null
)
