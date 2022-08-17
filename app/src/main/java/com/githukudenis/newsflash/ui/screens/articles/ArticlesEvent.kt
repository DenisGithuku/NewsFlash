package com.githukudenis.newsflash.ui.screens.articles

import com.githukudenis.newsflash.ui.util.ActiveScreen

sealed class ArticlesEvent {
    data class ChangeActiveScreen(val activeScreen: ActiveScreen): ArticlesEvent()
}
