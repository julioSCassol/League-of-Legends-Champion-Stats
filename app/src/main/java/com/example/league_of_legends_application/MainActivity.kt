package com.example.league_of_legends_application

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeagueOfLegendsApp()
        }
    }
}

@Composable
fun LeagueOfLegendsApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF101820)
    ) {
        ChampionScreen()
    }
}

@Composable
fun ChampionScreen() {
    var champions by remember { mutableStateOf<List<Champion>>(emptyList()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0A0F1E), Color(0xFF101820))
                )
            )
            .padding(16.dp)
    ) {
        Thread {
            try {
                val url = URL("http://girardon.com.br:3001/champions")
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    Log.d("Network", "Sent 'GET' request to URL: $url; Response Code: $responseCode")

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream.bufferedReader().use { reader ->
                            val jsonResponse = reader.readText()
                            champions = parseChampions(JSONArray(jsonResponse))
                        }
                    } else {
                        champions = emptyList()
                    }
                }
            } catch (e: Exception) {
                Log.e("Network", "Exception: ${e.localizedMessage}", e)
            }
        }.start()

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Champions",
                    fontSize = 28.sp,
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(champions) { champion ->
                ChampionCard(champion)
            }
        }
    }
}

@Composable
fun ChampionCard(champion: Champion) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E2A38)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconUrl = champion.icon
            val bitmap = loadImageFromUrl(iconUrl)

            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            Brush.radialGradient(
                                listOf(Color(0xFFFFD700), Color.Transparent)
                            )
                        )
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = champion.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
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

@Composable
fun loadImageFromUrl(url: String): Bitmap? {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(url) {
        withContext(Dispatchers.IO) {
            try {
                val input = URL(url).openStream()
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                Log.e("ImageLoad", "Error loading image: ${e.localizedMessage}", e)
            }
        }
    }
    return bitmap
}

fun parseChampions(jsonArray: JSONArray): List<Champion> {
    val champions = mutableListOf<Champion>()
    for (i in 0 until jsonArray.length()) {
        val championJson = jsonArray.getJSONObject(i)
        champions.add(parseChampion(championJson))
    }
    return champions
}

fun parseChampion(jsonObject: JSONObject): Champion {
    val id = jsonObject.getString("id")
    val key = jsonObject.getString("key")
    val name = jsonObject.getString("name")
    val title = jsonObject.getString("title")
    val tags = jsonObject.getJSONArray("tags").let { tagsArray ->
        List(tagsArray.length()) { tagsArray.getString(it) }
    }
    val icon = jsonObject.getString("icon")
    val description = jsonObject.getString("description")

    return Champion(id, key, name, title, tags, icon, description)
}

data class Champion(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val tags: List<String>,
    val icon: String,
    val description: String
)

@Preview(showBackground = true)
@Composable
fun ChampionScreenPreview() {
    LeagueOfLegendsApp()
}
