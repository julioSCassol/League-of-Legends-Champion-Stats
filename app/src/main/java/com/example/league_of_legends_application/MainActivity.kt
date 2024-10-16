package com.example.league_of_legends_application

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.league_of_legends_application.ui.screens.SplashScreen
import com.example.league_of_legends_application.ui.screens.AppNavigation
import com.example.league_of_legends_application.viewmodel.ChampionViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showSplashScreen by remember { mutableStateOf(true) }
            val viewModel = ChampionViewModel()

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

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result

            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("MainActivity", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
