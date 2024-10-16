package com.example.league_of_legends_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.league_of_legends_application.repositories.ChampionRepository
import com.example.league_of_legends_application.ui.screens.SplashScreen
import com.example.league_of_legends_application.ui.screens.AppNavigation
import com.example.league_of_legends_application.viewmodel.ChampionViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var showSplashScreen by remember { mutableStateOf(true) }
            val viewModel = ChampionViewModel(ChampionRepository(context))

            if (showSplashScreen) {
                SplashScreen(onLoadingFinished = { showSplashScreen = false })
                LaunchedEffect(key1 = true) {
                    delay(3000)
                    showSplashScreen = false
                }
            } else {
                AppNavigation(viewModel)
            }
        }
    }
}
