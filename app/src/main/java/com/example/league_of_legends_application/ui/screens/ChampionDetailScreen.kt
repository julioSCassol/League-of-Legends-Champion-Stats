package com.example.league_of_legends_application.ui.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

@Composable
fun ChampionDetailScreen(champion: Champion, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1923))
            .padding(16.dp)
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
            Image(
                bitmap = bitmap,
                contentDescription = champion.name,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color(0xFFDFD79B), CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
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

        StatsSection(stats = champion.stats)
    }
}

@Composable
fun StatsSection(stats: Stats) {
    Column {
        Text(
            text = "Atributos",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        AttributeBar("HP", stats.hp, stats.hpperlevel, Color(0xFFFF5353))
        AttributeBar("Mana", stats.mp, stats.mpperlevel, Color(0xFF5599FF))
        AttributeBar("Dano de Ataque", stats.attackdamage, stats.attackdamageperlevel, Color(0xFF4CAF50))
        AttributeBar("Velocidade de Ataque", (stats.attackspeed * 100).toInt(), (stats.attackspeedperlevel * 100).toInt(), Color(0xFFFFC107))
    }
}

@Composable
fun AttributeBar(attributeName: String, baseValue: Int, perLevelValue: Int, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = "$attributeName:",
            color = Color.White,
            modifier = Modifier.width(100.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(Color.Gray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width((baseValue / 10).dp)
                    .background(color)
            )
        }
    }
}
