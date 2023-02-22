package com.githukudenis.newsflash.ui.screens.home.sections.everything

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.githukudenis.newsflash.domain.model.Article
import com.githukudenis.newsflash.ui.screens.home.sections.headlines.components.ArticleItem
import com.githukudenis.newsflash.ui.theme.MediumBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EverythingSection(
    onSelectArticle: (Article) -> Unit,
    groupedArticles: Map<String, List<Article>>,
) {
    val articleListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showScrollButtonVisible = remember {
        derivedStateOf {
            articleListState.firstVisibleItemIndex > 5
        }
    }
    val scrollButtonColor = animateColorAsState(
        targetValue = if (showScrollButtonVisible.value) MediumBlue else Color.Transparent,
        animationSpec = tween(durationMillis = 500)
    )
    val scrollButtonIconColor = animateColorAsState(
        targetValue = if (showScrollButtonVisible.value) Color.White else Color.Transparent,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = articleListState, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                groupedArticles.forEach { (initial, articles) ->
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
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
                    itemsIndexed(items = articles,
                        key = { _: Int, item: Article -> item.title }) { index, article ->
                        ArticleItem(
                            article = article,
                            onSelectArticle = { onSelectArticle(article) },
                            modifier = Modifier.animateItemPlacement(
                                tween(500)
                            )
                        )
                        if (index < articles.size - 1) {
                            Divider(color = Color.Black.copy(alpha = 0.25f))
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }

            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(scrollButtonColor.value, shape = RoundedCornerShape(16.dp))
                .clickable {
                    scope.launch {
                        articleListState.animateScrollToItem(index = 0)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Scroll to the top",
                    modifier = Modifier.padding(12.dp),
                    tint = scrollButtonIconColor.value
                )
            }
        }
    }

}
