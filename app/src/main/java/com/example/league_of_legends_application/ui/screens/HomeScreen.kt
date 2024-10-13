package com.example.league_of_legends_application.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.league_of_legends_application.R

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onRandomizerClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1923))
    ) {
        Image(
            painter = painterResource(id = R.drawable.lologo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.3f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bem-Vindo ao League of Legends Champion Stats!",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDFD79B)
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1F8B4C),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Conheça os Campeões",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRandomizerClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDFD79B),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Criar Equipes Aleatórias",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

