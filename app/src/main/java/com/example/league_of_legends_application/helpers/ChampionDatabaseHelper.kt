package com.example.league_of_legends_application.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ChampionDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "champions.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "champions"
        const val COLUMN_ID = "id"
        const val COLUMN_KEY = "key"
        const val COLUMN_NAME = "name"
        const val COLUMN_TITLE = "title"
        const val COLUMN_TAGS = "tags"
        const val COLUMN_STATS = "stats"
        const val COLUMN_ICON = "icon"
        const val COLUMN_SPRITE_URL = "sprite_url"
        const val COLUMN_SPRITE_X = "sprite_x"
        const val COLUMN_SPRITE_Y = "sprite_y"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_KEY TEXT,
                $COLUMN_NAME TEXT,
                $COLUMN_TITLE TEXT,
                $COLUMN_TAGS TEXT,
                $COLUMN_STATS TEXT,
                $COLUMN_ICON TEXT,
                $COLUMN_SPRITE_URL TEXT,
                $COLUMN_SPRITE_X INTEGER,
                $COLUMN_SPRITE_Y INTEGER,
                $COLUMN_DESCRIPTION TEXT
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
