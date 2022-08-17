package com.githukudenis.newsflash.ui.screens.headlines

import com.githukudenis.newsflash.domain.model.SourceX

sealed interface HeadlinesEvent {
    data class Search(val query: String): HeadlinesEvent
    data class ChangeSource(val source: SourceX): HeadlinesEvent
}
