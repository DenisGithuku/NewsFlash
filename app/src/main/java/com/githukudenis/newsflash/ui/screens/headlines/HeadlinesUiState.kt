package com.githukudenis.newsflash.ui.screens.headlines

import com.githukudenis.newsflash.domain.model.Article
import com.githukudenis.newsflash.domain.model.SourceX

data class HeadlinesUiState(
    val sourcesLoading: Boolean = false,
    val headlinesLoading: Boolean = false,
    val headlineSources: List<SourceX> = emptyList(),
    val selectedSource: SourceX? = null,
    var searchQuery: String = "",
    val headlines: List<Article> = emptyList(),
    val error: String? = null
)
