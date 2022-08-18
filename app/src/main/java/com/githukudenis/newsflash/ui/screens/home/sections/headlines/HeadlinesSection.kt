package com.githukudenis.newsflash.ui.screens.home.sections.headlines

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.githukudenis.newsflash.domain.model.Article
import com.githukudenis.newsflash.domain.model.SourceX
import com.githukudenis.newsflash.ui.screens.home.sections.headlines.components.ArticleItem
import com.githukudenis.newsflash.ui.screens.home.sections.headlines.components.SourceItem
import com.githukudenis.newsflash.ui.screens.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HeadlinesSection(
    headlineSources: List<SourceX>,
    selectedHeadlineSource: SourceX?,
    onSelectHeadlineSource: (SourceX) -> Unit,
    onSelectArticle: (Article) -> Unit,
    headlines: List<Article>,
) {

    val sourcesListState = rememberLazyListState()
    val headlinesListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = sourcesListState,
            modifier = Modifier
        ) {
            itemsIndexed(headlineSources) { index, source ->
                SourceItem(
                    source = source,
                    selected = source == selectedHeadlineSource,
                    onSelect = {
                        onSelectHeadlineSource(source).also {
                            scope.launch {
                                sourcesListState.animateScrollToItem(
                                    index = index,
                                    scrollOffset = 3
                                )
                            }
                        }
                    })
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(state = headlinesListState) {
            itemsIndexed(headlines) { index, article ->
                ArticleItem(article = article, onSelectArticle = { selectedArticle ->
                       onSelectArticle(selectedArticle)
                })
                if (index < headlines.size - 1) {
                    Divider(color = Color.Black.copy(alpha = 0.25f))
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
