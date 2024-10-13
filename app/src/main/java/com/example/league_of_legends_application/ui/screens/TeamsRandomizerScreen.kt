package com.example.league_of_legends_application.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.league_of_legends_application.R
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.utils.loadImageFromUrl
import com.example.league_of_legends_application.viewmodel.ChampionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChampionRandomizerScreen(
    viewModel: ChampionViewModel,
    onBackClick: () -> Unit
) {
    val champions by viewModel.champions.collectAsState()
    var team1Champions by remember { mutableStateOf<List<Champion>>(emptyList()) }
    var team2Champions by remember { mutableStateOf<List<Champion>>(emptyList()) }
    var teamsRandomized by remember { mutableStateOf(false) }

    fun randomizeTeams() {
        val shuffledChampions = champions.shuffled()
        team1Champions = shuffledChampions.take(5)
        team2Champions = shuffledChampions.drop(5).take(5)
        teamsRandomized = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Randomizar Campeões", color = Color(0xFFDFD79B), fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                actions = {
                    Button(
                        onClick = { randomizeTeams() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDFD79B))
                    ) {
                        Text(text = "Randomizar", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF0F1923))
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1923))
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (teamsRandomized) {
                    Text(
                        "Equipe 1",
                        color = Color(0xFFDFD79B),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            team1Champions.take(3).forEach { champion ->
                                ChampionItem(champion)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            team1Champions.drop(3).take(2).forEach { champion ->
                                ChampionItem(champion)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.versus),
                        contentDescription = "Versus",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        "Equipe 2",
                        color = Color(0xFFDFD79B),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            team2Champions.take(3).forEach { champion ->
                                ChampionItem(champion)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            team2Champions.drop(3).take(2).forEach { champion ->
                                ChampionItem(champion)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChampionItem(champion: Champion) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
            .background(Color(0xFF1A2634), shape = MaterialTheme.shapes.medium)
    ) {
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        LaunchedEffect(champion.icon) {
            imageBitmap = loadImageFromUrl(champion.icon)
        }

        imageBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = champion.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = champion.name,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
