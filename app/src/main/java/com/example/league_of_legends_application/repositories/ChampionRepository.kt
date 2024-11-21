package com.example.league_of_legends_application.repositories

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.league_of_legends_application.helpers.ChampionDatabaseHelper
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.model.Sprite
import com.example.league_of_legends_application.model.Stats

class ChampionRepository(context: Context) {
    private val dbHelper = ChampionDatabaseHelper(context)
    private val TAG = "ChampionRepository"

    fun insertChampion(champion: Champion) {
        Log.d(TAG, "Inserting champion: ${champion.name}")
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ChampionDatabaseHelper.COLUMN_ID, champion.id)
            put(ChampionDatabaseHelper.COLUMN_NAME, champion.name)
            put(ChampionDatabaseHelper.COLUMN_TITLE, champion.title)
            put(ChampionDatabaseHelper.COLUMN_TAGS, champion.tags.joinToString(","))
            put(ChampionDatabaseHelper.COLUMN_STATS, serializeStats(champion
                .stats!!))
            put(ChampionDatabaseHelper.COLUMN_ICON, champion.icon)
            put(ChampionDatabaseHelper.COLUMN_SPRITE_URL, champion.sprite!!.url)
            put(ChampionDatabaseHelper.COLUMN_SPRITE_X, champion.sprite.x)
            put(ChampionDatabaseHelper.COLUMN_SPRITE_Y, champion.sprite.y)
            put(ChampionDatabaseHelper.COLUMN_DESCRIPTION, champion.description)
        }
        val result = db.insert(ChampionDatabaseHelper.TABLE_NAME, null, values)
        if (result == -1L) {
            Log.e(TAG, "Failed to insert champion: ${champion.name}")
        } else {
            Log.d(TAG, "Champion inserted successfully: ${champion.name}")
        }
        db.close()
    }

    fun getChampionById(id: String): Champion? {
        Log.d(TAG, "Retrieving champion by ID: $id")
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ChampionDatabaseHelper.TABLE_NAME,
            null,
            "${ChampionDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null
        )

        val champion = if (cursor.moveToFirst()) {
            Champion(
                id = cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_NAME)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_TITLE)),
                tags = cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_TAGS)).split(","),
                icon = cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_ICON)),
                sprite = Sprite(
                    url = cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_SPRITE_URL)),
                    x = cursor.getInt(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_SPRITE_X)),
                    y = cursor.getInt(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_SPRITE_Y))
                ),
                description = cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_DESCRIPTION)),
                stats = parseStats(cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_STATS)))
            )
        } else {
            Log.d(TAG, "Champion not found with ID: $id")
            null
        }

        cursor.close()
        db.close()
        return champion
    }

    fun getAllChampions(): List<Champion> {
        Log.d(TAG, "Retrieving all champions from cache")
        val champions = mutableListOf<Champion>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(ChampionDatabaseHelper.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val champion = Champion(
                    id = getString(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_NAME)),
                    title = getString(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_TITLE)),
                    tags = getString(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_TAGS)).split(","),
                    icon = getString(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_ICON)),
                    sprite = Sprite(
                        url = getString(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_SPRITE_URL)),
                        x = getInt(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_SPRITE_X)),
                        y = getInt(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_SPRITE_Y))
                    ),
                    description = getString(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_DESCRIPTION)),
                    stats = parseStats(getString(getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_STATS)))
                )
                champions.add(champion)
                Log.d(TAG, "Champion retrieved: ${champion.name}")
            }
        }

        cursor.close()
        db.close()
        Log.d(TAG, "Total champions retrieved: ${champions.size}")
        return champions
    }

    fun updateChampions(apiChampions: List<Champion>) {
        Log.d(TAG, "Updating champions in cache")
        val cachedChampions = getAllChampions()
        if (cachedChampions != apiChampions) {
            Log.d(TAG, "Cache is outdated. Updating with new data")
            clearCache()
            apiChampions.forEach { insertChampion(it) }
            Log.d(TAG, "Cache updated successfully")
        } else {
            Log.d(TAG, "Cache is already up-to-date")
        }
    }

    private fun clearCache() {
        Log.d(TAG, "Clearing all champions from cache")
        val db = dbHelper.writableDatabase
        db.delete(ChampionDatabaseHelper.TABLE_NAME, null, null)
        db.close()
    }

    private fun serializeStats(stats: Stats): String {
        return listOf(
            "hp=${stats.hp}",
            "hpperlevel=${stats.hpperlevel}",
            "mp=${stats.mp}",
            "mpperlevel=${stats.mpperlevel}",
            "movespeed=${stats.movespeed}",
            "armor=${stats.armor}",
            "armorperlevel=${stats.armorperlevel}",
            "spellblock=${stats.spellblock}",
            "spellblockperlevel=${stats.spellblockperlevel}",
            "attackrange=${stats.attackrange}",
            "hpregen=${stats.hpregen}",
            "hpregenperlevel=${stats.hpregenperlevel}",
            "mpregen=${stats.mpregen}",
            "mpregenperlevel=${stats.mpregenperlevel}",
            "crit=${stats.crit}",
            "critperlevel=${stats.critperlevel}",
            "attackdamage=${stats.attackdamage}",
            "attackdamageperlevel=${stats.attackdamageperlevel}",
            "attackspeedperlevel=${stats.attackspeedperlevel}",
            "attackspeed=${stats.attackspeed}"
        ).joinToString(",")
    }

    private fun parseStats(stats: String): Stats {
        val statMap = stats.split(",").associate {
            val (key, value) = it.split("=")
            key to (value.toDoubleOrNull() ?: value.toIntOrNull() ?: 0)
        }

        return Stats(
            hp = (statMap["hp"] as? Double)?.toInt() ?: statMap["hp"] as Int,
            hpperlevel = (statMap["hpperlevel"] as? Double)?.toInt() ?: statMap["hpperlevel"] as Int,
            mp = (statMap["mp"] as? Double)?.toInt() ?: statMap["mp"] as Int,
            mpperlevel = (statMap["mpperlevel"] as? Double)?.toInt() ?: statMap["mpperlevel"] as Int,
            movespeed = (statMap["movespeed"] as? Double)?.toInt() ?: statMap["movespeed"] as Int,
            armor = (statMap["armor"] as? Double) ?: statMap["armor"] as Double,
            armorperlevel = (statMap["armorperlevel"] as? Double) ?: statMap["armorperlevel"] as Double,
            spellblock = (statMap["spellblock"] as? Double) ?: statMap["spellblock"] as Double,
            spellblockperlevel = (statMap["spellblockperlevel"] as? Double) ?: statMap["spellblockperlevel"] as Double,
            attackrange = (statMap["attackrange"] as? Double)?.toInt() ?: statMap["attackrange"] as Int,
            hpregen = (statMap["hpregen"] as? Double) ?: statMap["hpregen"] as Double,
            hpregenperlevel = (statMap["hpregenperlevel"] as? Double) ?: statMap["hpregenperlevel"] as Double,
            mpregen = (statMap["mpregen"] as? Double)?.toInt() ?: statMap["mpregen"] as Int,
            mpregenperlevel = (statMap["mpregenperlevel"] as? Double)?.toInt() ?: statMap["mpregenperlevel"] as Int,
            crit = (statMap["crit"] as? Double)?.toInt() ?: statMap["crit"] as Int,
            critperlevel = (statMap["critperlevel"] as? Double)?.toInt() ?: statMap["critperlevel"] as Int,
            attackdamage = (statMap["attackdamage"] as? Double)?.toInt() ?: statMap["attackdamage"] as Int,
            attackdamageperlevel = (statMap["attackdamageperlevel"] as? Double)?.toInt() ?: statMap["attackdamageperlevel"] as Int,
            attackspeedperlevel = (statMap["attackspeedperlevel"] as? Double) ?: statMap["attackspeedperlevel"] as Double,
            attackspeed = (statMap["attackspeed"] as? Double) ?: statMap["attackspeed"] as Double
        )
    }
}
