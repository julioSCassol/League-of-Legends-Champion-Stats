package com.example.league_of_legends_application

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.league_of_legends_application.helpers.ChampionDatabaseHelper
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNotEquals
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [29], manifest = Config.NONE)
class ChampionDatabaseHelperTest {

    private lateinit var dbHelper: ChampionDatabaseHelper
    private lateinit var database: SQLiteDatabase

    @Before
    fun setUp() {
        dbHelper = ChampionDatabaseHelper(ApplicationProvider.getApplicationContext())
        database = dbHelper.writableDatabase
    }

    @After
    fun tearDown() {
        database.close()
        dbHelper.close()
    }

    @Test
    fun testDatabaseCreation() {
        assertNotNull("Database should not be null", database)
        val cursor = database.rawQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='${ChampionDatabaseHelper.TABLE_NAME}'",
            null
        )
        assertEquals("Champions table should exist in the database", 1, cursor.count)
        cursor.close()
    }

    @Test
    fun testInsertAndQueryChampion() {
        val values = ContentValues().apply {
            put(ChampionDatabaseHelper.COLUMN_ID, "1")
            put(ChampionDatabaseHelper.COLUMN_NAME, "Aatrox")
            put(ChampionDatabaseHelper.COLUMN_TITLE, "the Darkin Blade")
            put(ChampionDatabaseHelper.COLUMN_TAGS, "Fighter,Tank")
            put(ChampionDatabaseHelper.COLUMN_STATS, "{hp: 580, mp: 0}")
            put(ChampionDatabaseHelper.COLUMN_ICON, "icon_url")
            put(ChampionDatabaseHelper.COLUMN_SPRITE_URL, "sprite_url")
            put(ChampionDatabaseHelper.COLUMN_SPRITE_X, 10)
            put(ChampionDatabaseHelper.COLUMN_SPRITE_Y, 20)
            put(ChampionDatabaseHelper.COLUMN_DESCRIPTION, "Some description")
        }

        val rowId = database.insert(ChampionDatabaseHelper.TABLE_NAME, null, values)
        assertNotEquals("Insert operation should return a valid row ID", -1, rowId)

        val cursor = database.query(
            ChampionDatabaseHelper.TABLE_NAME,
            null,
            "${ChampionDatabaseHelper.COLUMN_ID} = ?",
            arrayOf("1"),
            null,
            null,
            null
        )

        assertEquals("There should be one entry in the database", 1, cursor.count)
        if (cursor.moveToFirst()) {
            assertEquals("1", cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_ID)))
            assertEquals("the Darkin Blade", cursor.getString(cursor.getColumnIndexOrThrow(ChampionDatabaseHelper.COLUMN_TITLE)))
        }
        cursor.close()
    }

    @Test
    fun testUpgradeDatabase() {
        dbHelper.onUpgrade(database, 1, 2)

        val cursor = database.rawQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='${ChampionDatabaseHelper.TABLE_NAME}'",
            null
        )
        assertEquals("Champions table should be recreated after upgrade", 1, cursor.count)
        cursor.close()
    }
}
