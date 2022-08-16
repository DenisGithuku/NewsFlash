package com.githukudenis.newsflash.domain.model

data class ArticleDTO(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
