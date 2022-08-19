package com.githukudenis.newsflash.ui.screens.article_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.githukudenis.newsflash.domain.model.Article
import com.ramcosta.composedestinations.annotation.Destination

@Destination(route = "article_details")
@Composable
fun ArticleDetails(
    article: Article
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = article.title)
    }

}
