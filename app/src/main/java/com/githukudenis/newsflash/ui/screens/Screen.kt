package com.githukudenis.newsflash.ui.screens

sealed class Screen(val route: String) {
    object HeadLines: Screen("articles")
}
