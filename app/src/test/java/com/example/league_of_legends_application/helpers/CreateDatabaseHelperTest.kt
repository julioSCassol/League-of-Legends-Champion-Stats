package com.example.league_of_legends_application.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampionDatabaseHelperTest {

    private lateinit var dbHelper: ChampionDatabaseHelper
    private lateinit var db: SQLiteDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        dbHelper = ChampionDatabaseHelper(context)
        db = dbHelper.writableDatabase
    }

    @After
    fun tearDown() {
        db.close()
        dbHelper.close()
        context.deleteDatabase(ChampionDatabaseHelper.DATABASE_NAME)
    }

    @Test
    fun testDatabaseCreation() {
        assertTrue(db.isOpen)
    }

    @Test
    fun testTableExists() {
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='${ChampionDatabaseHelper.TABLE_NAME}'", null)
        assertNotNull(cursor)
        assertTrue(cursor.moveToFirst())
        cursor.close()
    }

    @Test
    fun testTableColumns() {
        val cursor = db.rawQuery("PRAGMA table_info(${ChampionDatabaseHelper.TABLE_NAME})", null)
        val columns = mutableListOf<String>()
        while (cursor.moveToNext()) {
            columns.add(cursor.getString(cursor.getColumnIndex("name")))
        }
        cursor.close()

        assertTrue(columns.containsAll(listOf(
            ChampionDatabaseHelper.COLUMN_ID,
            ChampionDatabaseHelper.COLUMN_KEY,
            ChampionDatabaseHelper.COLUMN_NAME,
            ChampionDatabaseHelper.COLUMN_TITLE,
            ChampionDatabaseHelper.COLUMN_TAGS,
            ChampionDatabaseHelper.COLUMN_STATS,
            ChampionDatabaseHelper.COLUMN_ICON,
            ChampionDatabaseHelper.COLUMN_SPRITE_URL,
            ChampionDatabaseHelper.COLUMN_SPRITE_X,
            ChampionDatabaseHelper.COLUMN_SPRITE_Y,
            ChampionDatabaseHelper.COLUMN_DESCRIPTION
        )))
    }

    @Test
    fun testOnUpgrade() {
        dbHelper.onUpgrade(db, 1, 2)

        // Check if table still exists after upgrade
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='${ChampionDatabaseHelper.TABLE_NAME}'", null)
        assertNotNull(cursor)
        assertTrue(cursor.moveToFirst())
        cursor.close()

        // Check if columns are intact
        val tableInfoCursor = db.rawQuery("PRAGMA table_info(${ChampionDatabaseHelper.TABLE_NAME})", null)
        val columns = mutableListOf<String>()
        while (tableInfoCursor.moveToNext()) {
            columns.add(tableInfoCursor.getString(tableInfoCursor.getColumnIndex("name")))
        }
        tableInfoCursor.close()

        assertTrue(columns.containsAll(listOf(
            ChampionDatabaseHelper.COLUMN_ID,
            ChampionDatabaseHelper.COLUMN_KEY,
            ChampionDatabaseHelper.COLUMN_NAME,
            ChampionDatabaseHelper.COLUMN_TITLE,
            ChampionDatabaseHelper.COLUMN_TAGS,
            ChampionDatabaseHelper.COLUMN_STATS,
            ChampionDatabaseHelper.COLUMN_ICON,
            ChampionDatabaseHelper.COLUMN_SPRITE_URL,
            ChampionDatabaseHelper.COLUMN_SPRITE_X,
            ChampionDatabaseHelper.COLUMN_SPRITE_Y,
            ChampionDatabaseHelper.COLUMN_DESCRIPTION
        )))
    }

    @Test(expected = SQLiteException::class)
    fun testInsertInvalidDataType() {
        // Attempt to insert incorrect data type in INTEGER column
        val insertQuery = """
            INSERT INTO ${ChampionDatabaseHelper.TABLE_NAME} (
                ${ChampionDatabaseHelper.COLUMN_ID},
                ${ChampionDatabaseHelper.COLUMN_SPRITE_X}
            ) VALUES ('invalid_id', 'string_in_integer_column')
        """
        db.execSQL(insertQuery)
    }
}
