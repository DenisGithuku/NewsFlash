package com.githukudenis.newsflash.ui.screens.headlines

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.githukudenis.newsflash.ui.screens.headlines.components.ArticleItem
import com.githukudenis.newsflash.ui.screens.headlines.components.SourceItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Destination(route = "headlines")
@Composable
fun HeadlinesScreen(
    navigator: DestinationsNavigator,
) {

    val headlinesViewModel: HeadlinesViewModel = hiltViewModel()
    val uiState = headlinesViewModel.uiState.collectAsState()
    val sourcesListState = rememberLazyListState()
    val headlinesListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    uiState.value.error?.let { error ->
        if (error.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error, color = Color.Red, textAlign = TextAlign.Center)
            }
            return
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Headlines",
            style = TextStyle(fontSize = 16.sp, color = Color.Black.copy(alpha = 0.7f))
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (uiState.value.headlinesLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyRow(
                state = sourcesListState,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                itemsIndexed(uiState.value.headlineSources) { index, source ->
                    SourceItem(
                        source = source,
                        selected = source == uiState.value.selectedSource,
                        onSelect = {
                            headlinesViewModel.onEvent(HeadlinesEvent.ChangeSource(source)).also {
                                scope.launch(Dispatchers.Main) {
                                    sourcesListState.scrollToItem(index)
                                }
                            }
                        })
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (uiState.value.headlinesLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(state = headlinesListState) {
                itemsIndexed(uiState.value.headlines) { index, article ->
                    ArticleItem(article = article, onSelectArticle = { selectedArticle ->
//                       navigator.navigate(ArticleDetailsDestination(selectedArticle))
                    })
                    if(index < uiState.value.headlines.size - 1) {
                        Divider(color = Color.Black.copy(alpha = 0.25f))
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }


}
