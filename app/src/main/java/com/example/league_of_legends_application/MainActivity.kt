package com.example.league_of_legends_application

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChampionScreen()
        }
    }
}

@Composable
fun ChampionScreen() {
    var champions by remember { mutableStateOf<List<Champion>>(emptyList()) }

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

    Column(modifier = Modifier.padding(16.dp)) {
        champions.forEach { champion ->
            ChampionCard(champion)
        }
    }
}

@Composable
fun ChampionCard(champion: Champion) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            val iconUrl = champion.icon
            val bitmap = loadImageFromUrl(iconUrl)

            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = champion.name)
                Text(text = champion.title)
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
    ChampionScreen()
}
