package com.example.league_of_legends_application.network

import android.util.Log
import com.example.league_of_legends_application.model.Item
import com.example.league_of_legends_application.model.Price
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ItemService {
    private const val TAG = "ItemService"

    suspend fun fetchItems(size: Int? = 10, page: Int? = 1): List<Item> {
        return withContext(Dispatchers.IO) {
            val url = URL("http://girardon.com.br:3001/items?page=${page}&size=${size}")
            val connection = url.openConnection() as HttpURLConnection
            try {
                Log.d(TAG, "Attempting to fetch items")
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "Connection successful. Response code: $responseCode")

                    val reader = InputStreamReader(connection.inputStream)
                    val jsonResponse = reader.readText()

                    Log.d(TAG, "Response received: $jsonResponse")
                    parseItems(JSONArray(jsonResponse)).also {
                        Log.d(TAG, "Parsed ${it.size} items successfully")
                    }
                } else {
                    Log.e(TAG, "Failed to fetch items. Response code: $responseCode")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching items: ${e.message}", e)
                emptyList()
            } finally {
                connection.disconnect()
                Log.d(TAG, "Connection closed")
            }
        }
    }

    private fun parseItems(jsonArray: JSONArray): List<Item> {
        val items = mutableListOf<Item>()
        try {
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val priceObject = jsonObject.getJSONObject("price")

                items.add(
                    Item(
                        name = jsonObject.getString("name"),
                        description = jsonObject.getString("description"),
                        price = Price(
                            base = priceObject.getInt("base"),
                            total = priceObject.getInt("total"),
                            sell = priceObject.getInt("sell")
                        ),
                        purchasable = jsonObject.getBoolean("purchasable"),
                        icon = jsonObject.getString("icon")
                    )
                )
            }
            Log.d(TAG, "Item parsing completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing items: ${e.message}", e)
        }
        return items
    }
}
