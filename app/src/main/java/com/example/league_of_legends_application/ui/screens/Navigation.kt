package com.example.league_of_legends_application.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.viewmodel.ChampionViewModel
import com.example.league_of_legends_application.viewmodel.ItemViewModel

sealed class Screen {
    data object Home : Screen()
    data object ChampionList : Screen()
    data class ChampionDetail(val champion: Champion) : Screen()
    data object TeamsRandomizerScreen : Screen()
}

@Composable
fun AppNavigation(championViewModel: ChampionViewModel, itemViewModel:
ItemViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    BackHandler(enabled = currentScreen != Screen.Home) {
        currentScreen = Screen.Home
    }

    when (val screen = currentScreen) {
        is Screen.Home -> HomeScreen(
            onStartClick = {
                currentScreen = Screen.ChampionList
            },
            onRandomizerClick = {
                currentScreen = Screen.TeamsRandomizerScreen
            }
        )

        is Screen.ChampionList -> ChampionListScreen(
            championViewModel = championViewModel,
            onChampionClick = {
                currentScreen = Screen.ChampionDetail(it)
            },
            onBackClick = {
                currentScreen = Screen.Home
            }
        )

        is Screen.ChampionDetail -> ChampionDetailScreen(
            champion = screen.champion,
            onBackClick = {
                currentScreen = Screen.ChampionList
            }
        )

        is Screen.TeamsRandomizerScreen -> ChampionRandomizerScreen(
            viewModel = championViewModel,
            itemViewModel = itemViewModel,
            onBackClick = {
                currentScreen = Screen.Home
            }
        )
    }
}
