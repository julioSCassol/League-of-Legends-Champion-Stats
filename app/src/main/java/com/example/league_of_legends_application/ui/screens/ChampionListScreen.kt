package com.example.league_of_legends_application.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.utils.loadImageFromUrl
import com.example.league_of_legends_application.viewmodel.ChampionViewModel
import com.example.league_of_legends_application.R

@Composable
fun ChampionListScreen(
    championViewModel: ChampionViewModel,
    onChampionClick: (Champion) -> Unit,
    onBackClick: () -> Unit
) {
    val champions by championViewModel.champions.collectAsState()
    val isLoading by championViewModel.isLoading.collectAsState()
    var selectedTag by remember { mutableStateOf<String?>(null) }
    val tags = listOf("Fighter", "Marksman", "Tank", "Mage", "Assassin")
    var searchQuery by remember { mutableStateOf("") }

    if (isLoading && champions.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFFDFD79B))
        }
    } else {
        Column(
            modifier = Modifier
                .background(Color(0xFF0F1923))
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onBackClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A5A5A))
                ) {
                    Text(text = stringResource(id = R.string.back_button_label), color = Color.White)
                }
            }

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(text = stringResource(id = R.string.search_placeholder), color = Color(0xFFDFD79B)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1E2A38),
                    unfocusedContainerColor = Color(0xFF1E2A38),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                tags.forEach { tag ->
                    Button(
                        onClick = {
                            selectedTag = if (selectedTag == tag) null else tag
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTag == tag) Color(0xFFDFD79B) else Color.LightGray
                        )
                    ) {
                        TagIcon(tag = tag)
                    }
                }
            }

            val filteredChampions = champions.filter {
                (selectedTag == null || it.tags.contains(selectedTag)) &&
                        it.name.contains(searchQuery, ignoreCase = true)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(filteredChampions) { champion ->
                    ChampionCard(champion = champion, onClick = { onChampionClick(champion) })
                }

                item {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            color = Color(0xFFDFD79B)
                        )
                    } else {
                        LaunchedEffect(Unit) {
                            championViewModel.loadNextPage()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TagIcon(tag: String) {
    val iconResId = when (tag) {
        "Fighter" -> R.drawable.fighter_icon
        "Marksman" -> R.drawable.marksman_icon
        "Tank" -> R.drawable.tank_icon
        "Mage" -> R.drawable.mage_icon
        "Assassin" -> R.drawable.slayer_icon
        else -> null
    }

    iconResId?.let {
        Image(
            painter = painterResource(id = it),
            contentDescription = "$tag icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ChampionCard(champion: Champion, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E2A38),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
            LaunchedEffect(champion.icon) {
                imageBitmap = loadImageFromUrl(champion.icon)
            }

            imageBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = champion.name,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = champion.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDFD79B)
                )
                Text(
                    text = champion.title,
                    fontSize = 16.sp,
                    color = Color(0xFF9A9DA1)
                )
            }
        }
    }
}
