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
            .background(Color(0xFF2E3B4E))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onBackClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "Voltar", color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = champion.name,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.White
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
                    .border(4.dp, Color.White, CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Classe: ${champion.tags.joinToString()}",
            color = Color.Cyan,
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
        Text(text = "Atributos", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        AttributeBar("HP", stats.hp, stats.hpperlevel, Color.Red)
        AttributeBar("Mana", stats.mp, stats.mpperlevel, Color.Blue)
        AttributeBar("Dano de Ataque", stats.attackdamage, stats.attackdamageperlevel, Color.Green)
        AttributeBar("Velocidade de Ataque", (stats.attackspeed * 100).toInt(), (stats.attackspeedperlevel * 100).toInt(), Color.Yellow)
    }
}

@Composable
fun AttributeBar(attributeName: String, baseValue: Int, perLevelValue: Int, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(text = "$attributeName:", color = Color.White, modifier = Modifier.width(100.dp))
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
