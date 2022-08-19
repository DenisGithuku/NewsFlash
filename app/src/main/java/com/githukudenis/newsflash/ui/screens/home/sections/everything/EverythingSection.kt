package com.githukudenis.newsflash.ui.screens.home.sections.everything

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.githukudenis.newsflash.domain.model.Article
import com.githukudenis.newsflash.ui.screens.home.sections.headlines.components.ArticleItem
import com.githukudenis.newsflash.ui.theme.MediumBlue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EverythingSection(
    onSelectArticle: (Article) -> Unit,
    allArticles: List<Article>,
) {
    val articleListState = rememberLazyListState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(state = articleListState, horizontalAlignment = Alignment.CenterHorizontally) {
            val groupedArticles = allArticles.groupBy { it.publishedAt }
            groupedArticles.forEach { (initial, articles) ->
                stickyHeader {
                    Box(
                        modifier = Modifier.padding(10.dp)
                            .background(color = MediumBlue, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initial.substringBefore('T'),
                            modifier = Modifier.padding(8.dp),
                            style = TextStyle(
                                color = Color.White, fontSize = 10.sp
                            )
                        )
                    }
                }
                items(articles) { article ->
                    ArticleItem(article = article, onSelectArticle = { onSelectArticle(article) })
                }
            }
        }
    }

}
