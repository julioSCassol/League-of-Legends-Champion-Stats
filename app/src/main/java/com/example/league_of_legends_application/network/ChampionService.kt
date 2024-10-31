package com.example.league_of_legends_application.network

import android.util.Log
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.model.Sprite
import com.example.league_of_legends_application.model.Stats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ChampionService {
    private const val TAG = "ChampionService"

    var baseUrl: String = "http://girardon.com.br:3001/"

    suspend fun fetchChampions(size: Int? = 10, page: Int? = 1): List<Champion> {
        return withContext(Dispatchers.IO) {
            val url = URL("${baseUrl}champions?page=${page}&size=${size}")
            val connection = url.openConnection() as HttpURLConnection
            try {
                Log.d(TAG, "Attempting to fetch champions from $url")
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "Connection successful. Response code: $responseCode")

                    val reader = InputStreamReader(connection.inputStream)
                    val jsonResponse = reader.readText()

                    Log.d(TAG, "Response received: $jsonResponse")
                    parseChampions(JSONArray(jsonResponse)).also {
                        Log.d(TAG, "Parsed ${it.size} champions successfully")
                    }
                } else {
                    Log.e(TAG, "Failed to fetch champions. Response code: $responseCode")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching champions: ${e.message}", e)
                emptyList()
            } finally {
                connection.disconnect()
                Log.d(TAG, "Connection closed")
            }
        }
    }

    fun parseChampions(jsonArray: JSONArray): List<Champion> {
        val champions = mutableListOf<Champion>()
        try {
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val spriteObject = jsonObject.getJSONObject("sprite")
                val statsObject = jsonObject.getJSONObject("stats")

                champions.add(
                    Champion(
                        id = jsonObject.getString("id"),
                        key = jsonObject.getString("key"),
                        name = jsonObject.getString("name"),
                        title = jsonObject.getString("title"),
                        tags = jsonObject.getJSONArray("tags").let { tagsArray ->
                            List(tagsArray.length()) { tagsArray.getString(it) }
                        },
                        icon = jsonObject.getString("icon"),
                        description = jsonObject.getString("description"),
                        sprite = Sprite(
                            url = spriteObject.getString("url"),
                            x = spriteObject.getInt("x"),
                            y = spriteObject.getInt("y")
                        ),
                        stats = Stats(
                            hp = statsObject.getInt("hp"),
                            hpperlevel = statsObject.getInt("hpperlevel"),
                            mp = statsObject.getInt("mp"),
                            mpperlevel = statsObject.getInt("mpperlevel"),
                            movespeed = statsObject.getInt("movespeed"),
                            armor = statsObject.getDouble("armor"),
                            armorperlevel = statsObject.getDouble("armorperlevel"),
                            spellblock = statsObject.getDouble("spellblock"),
                            spellblockperlevel = statsObject.getDouble("spellblockperlevel"),
                            attackrange = statsObject.getInt("attackrange"),
                            hpregen = statsObject.getDouble("hpregen"),
                            hpregenperlevel = statsObject.getDouble("hpregenperlevel"),
                            mpregen = statsObject.getInt("mpregen"),
                            mpregenperlevel = statsObject.getInt("mpregenperlevel"),
                            crit = statsObject.getInt("crit"),
                            critperlevel = statsObject.getInt("critperlevel"),
                            attackdamage = statsObject.getInt("attackdamage"),
                            attackdamageperlevel = statsObject.getInt("attackdamageperlevel"),
                            attackspeedperlevel = statsObject.getDouble("attackspeedperlevel"),
                            attackspeed = statsObject.getDouble("attackspeed")
                        )
                    )
                )
            }
            Log.d(TAG, "Champion parsing completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing champions: ${e.message}", e)
        }
        return champions
    }
}
