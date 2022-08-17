package com.githukudenis.newsflash.ui.screens.articles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.githukudenis.newsflash.ui.screens.articles.components.ActiveScreenItem
import com.githukudenis.newsflash.ui.screens.everything.EverythingScreen
import com.githukudenis.newsflash.ui.screens.headlines.HeadlinesScreen
import com.githukudenis.newsflash.ui.util.ActiveScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterialApi::class)
@Destination(route = "articles", start = true)
@Composable
fun ArticlesScreen(
    navigator: DestinationsNavigator,
) {

    val articlesViewModel: ArticlesViewModel = hiltViewModel()
    val uiState = articlesViewModel.uiState.collectAsState()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetElevation = 12.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
                       Text("testing")
        },
        sheetPeekHeight = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyRow(
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp)
            ) {
                items(uiState.value.availableScreens) { activeScreen ->
                    ActiveScreenItem(
                        selected = activeScreen == uiState.value.activeScreen, onSelect = {
                            articlesViewModel.onEvent(
                                ArticlesEvent.ChangeActiveScreen(
                                    activeScreen
                                )
                            )
                        }, activeScreen = activeScreen
                    )
                }
            }
            when (uiState.value.activeScreen) {
                ActiveScreen.HEADLINES -> HeadlinesScreen(navigator = navigator)
                ActiveScreen.EVERYTHING -> EverythingScreen(navigator = navigator)
            }
        }
    }

}
