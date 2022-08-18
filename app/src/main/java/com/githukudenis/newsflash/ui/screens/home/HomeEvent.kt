package com.githukudenis.newsflash.ui.screens.home

import com.githukudenis.newsflash.domain.model.SourceX
import com.githukudenis.newsflash.ui.util.ActiveScreen

sealed interface HomeEvent {
    data class ChangeActiveScreen(val activeScreen: ActiveScreen): HomeEvent
    data class Search(val query: String): HomeEvent
    data class ChangeSource(val source: SourceX): HomeEvent
}
