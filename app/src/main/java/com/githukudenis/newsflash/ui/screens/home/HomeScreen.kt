package com.githukudenis.newsflash.ui.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.githukudenis.newsflash.ui.screens.destinations.ArticleDetailsDestination
import com.githukudenis.newsflash.ui.screens.home.sections.everything.components.ActiveScreenItem
import com.githukudenis.newsflash.ui.screens.home.sections.everything.EverythingSection
import com.githukudenis.newsflash.ui.screens.home.sections.headlines.HeadlinesSection
import com.githukudenis.newsflash.ui.util.ActiveScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "everything", start = true)
@Composable
fun EverythingScreen(
    navigator: DestinationsNavigator,
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState = homeViewModel.uiState.collectAsState().value
    val articles = uiState.allNews.groupBy { article -> article.publishedAt.substring(0..9) }

    Log.i("grouped_articles", articles.toString())

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
        ) {
            items(uiState.availableScreens) { activeScreen ->
                ActiveScreenItem(
                    selected = activeScreen == uiState.activeScreen, onSelect = {
                        homeViewModel.onEvent(
                            HomeEvent.ChangeActiveScreen(
                                activeScreen
                            )
                        )
                    }, activeScreen = activeScreen
                )
            }
        }


        when (uiState.activeScreen) {
            ActiveScreen.HEADLINES -> {
                AnimatedVisibility(visible = uiState.sourcesLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (uiState.errorMessages.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        for (errorMessage in uiState.errorMessages) {
                            Text(
                                text = errorMessage.error?.message!!,
                                textAlign = TextAlign.Center,
                                color = Color.Red.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
                HeadlinesSection(headlineSources = uiState.headlineSources,
                    selectedHeadlineSource = uiState.selectedSource,
                    onSelectHeadlineSource = { source ->
                        homeViewModel.onEvent(HomeEvent.ChangeSource(source))
                    },
                    headlines = uiState.headlines,
                    onSelectArticle = { article ->
                        navigator.navigate(ArticleDetailsDestination(article))
                    })
            }
            ActiveScreen.EVERYTHING -> {
                AnimatedVisibility(visible = uiState.allArticlesLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                EverythingSection(onSelectArticle = { article ->
                    navigator.navigate(ArticleDetailsDestination(article))
                }, groupedArticles = articles)
            }
        }

    }

}
