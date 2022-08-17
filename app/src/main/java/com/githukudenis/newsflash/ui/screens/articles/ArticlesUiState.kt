package com.githukudenis.newsflash.ui.screens.articles

import com.githukudenis.newsflash.ui.util.ActiveScreen

data class ArticlesUiState(
    val activeScreen: ActiveScreen = ActiveScreen.HEADLINES,
    val availableScreens: List<ActiveScreen> = listOf(ActiveScreen.HEADLINES, ActiveScreen.EVERYTHING)
)
