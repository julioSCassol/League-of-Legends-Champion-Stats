package com.example.league_of_legends_application.ui.screens

import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.ImageBitmap
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.model.Stats
import com.example.league_of_legends_application.utils.loadImageFromUrl
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.league_of_legends_application.R
import com.example.league_of_legends_application.utils.ChampionSounds
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun ChampionDetailScreen(champion: Champion, onBackClick: () -> Unit) {
    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    var showSirenIcon by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1923))
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onBackClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A5A5A),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Voltar")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = champion.name,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFFDFD79B)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        LaunchedEffect(champion.icon) {
            imageBitmap = loadImageFromUrl(champion.icon)
        }

        imageBitmap?.let { bitmap ->
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color(0xFFDFD79B), CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        mediaPlayer?.release()
                        mediaPlayer = ChampionSounds.playChampionSound(context, champion.name)
                        showSirenIcon = false
                    }
            ) {
                Image(
                    bitmap = bitmap,
                    contentDescription = champion.name,
                    modifier = Modifier.fillMaxSize()
                )

                if (showSirenIcon) {
                    Image(
                        painter = painterResource(id = R.drawable.megaphone),
                        contentDescription = "Siren Icon",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.TopEnd)
                            .padding(25.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Classe: ${champion.tags.joinToString()}",
            color = Color(0xFFDFD79B),
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = champion.description,
            color = Color.LightGray,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        StatsSection(stats = champion.stats!!)
    }
}

@Composable
fun StatsSection(stats: Stats) {
    var hoveredAttribute by remember { mutableStateOf<String?>(null) }

    Column {
        Text(
            text = "Atributos",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))

        AttributeBar(
            attributeName = "Vida Total",
            baseValue = stats.hp,
            perLevelValue = stats.hpperlevel,
            color = Color(0xFFFF5353),
            iconResId = R.drawable.health,
            hoveredAttribute = hoveredAttribute,
            onHover = { hoveredAttribute = "hp" }
        )
        if (hoveredAttribute == "hp") {
            RelatedAttribute(
                attributeName = "Vida por Nível",
                value = stats.hpperlevel,
                iconResId = R.drawable.health
            )
        }

        AttributeBar(
            attributeName = "Mana",
            baseValue = stats.mp,
            perLevelValue = stats.mpperlevel,
            color = Color(0xFF5599FF),
            iconResId = R.drawable.mana,
            hoveredAttribute = hoveredAttribute,
            onHover = { hoveredAttribute = "mp" }
        )
        if (hoveredAttribute == "mp") {
            RelatedAttribute(
                attributeName = "Mana por Nível",
                value = stats.mpperlevel,
                iconResId = R.drawable.mana
            )
        }

        AttributeBar(
            attributeName = "Dano de Ataque",
            baseValue = stats.attackdamage,
            perLevelValue = stats.attackdamageperlevel,
            color = Color(0xFF4CAF50),
            iconResId = R.drawable.attack,
            hoveredAttribute = hoveredAttribute,
            onHover = { hoveredAttribute = "attackdamage" }
        )
        if (hoveredAttribute == "attackdamage") {
            RelatedAttribute(
                attributeName = "Dano por Nível",
                value = stats.attackdamageperlevel,
                iconResId = R.drawable.attack
            )
        }

        AttributeBar(
            attributeName = "Velocidade de Ataque",
            baseValue = (stats.attackspeed * 100).toInt(),
            perLevelValue = (stats.attackspeedperlevel * 100).toInt(),
            color = Color(0xFFFFC107),
            iconResId = R.drawable.attack_speed,
            hoveredAttribute = hoveredAttribute,
            onHover = { hoveredAttribute = "attackspeed" }
        )
        if (hoveredAttribute == "attackspeed") {
            RelatedAttribute(
                attributeName = "Velocidade por Nível",
                value = (stats.attackspeedperlevel * 100).toInt(),
                iconResId = R.drawable.attack_speed
            )
        }
    }
}

@Composable
fun AttributeBar(
    attributeName: String,
    baseValue: Int,
    perLevelValue: Int,
    color: Color,
    iconResId: Int,
    hoveredAttribute: String?,
    onHover: () -> Unit
) {
    val barFixedWidth = 250.dp

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable(onClick = onHover)
    ) {
        Text(
            text = attributeName,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(barFixedWidth)
                    .height(20.dp)
                    .background(Color.Gray)
            ) {
                val barWidth = (baseValue.toFloat() / 1000) * barFixedWidth.value

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(barWidth.dp)
                        .background(color)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$baseValue",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 4.dp)
                )

                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = attributeName,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun RelatedAttribute(attributeName: String, value: Int, iconResId: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
    ) {
        Text(
            text = "$attributeName: $value",
            color = Color.LightGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.width(4.dp))

        Image(
            painter = painterResource(id = iconResId),
            contentDescription = attributeName,
            modifier = Modifier.size(20.dp)
        )
    }
}
