package com.example.league_of_legends_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.league_of_legends_application.repositories.ChampionRepository
import com.example.league_of_legends_application.ui.screens.SplashScreen
import com.example.league_of_legends_application.ui.screens.AppNavigation
import com.example.league_of_legends_application.viewmodel.ChampionViewModel
import com.example.league_of_legends_application.viewmodel.ItemViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showSplashScreen by remember { mutableStateOf(true) }
            val championRepository = ChampionRepository(context = applicationContext)

            val championViewModel = ChampionViewModel(repository = championRepository)
            val itemViewModel = ItemViewModel()

            if (showSplashScreen) {
                SplashScreen(onLoadingFinished = { showSplashScreen = false })
                LaunchedEffect(key1 = true) {
                    delay(3000)
                    showSplashScreen = false
                }
            } else {
                AppNavigation(championViewModel, itemViewModel)
            }
        }
    }
}