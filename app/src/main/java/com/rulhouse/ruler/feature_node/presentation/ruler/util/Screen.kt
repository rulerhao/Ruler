package com.rulhouse.ruler.feature_node.presentation.ruler.util

sealed class Screen (val route: String) {
    object RulerScreen: Screen("ruler_screen")
}