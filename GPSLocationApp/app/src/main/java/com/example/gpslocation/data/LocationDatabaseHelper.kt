package com.example.gpslocation.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * SQLite helper class for storing location data.
 */
class LocationDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_LAT REAL, " +
                "$COLUMN_LON REAL, " +
                "$COLUMN_TIME INTEGER, " +
                "$COLUMN_REGION TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insert(location: Location): Long {
        val values = ContentValues().apply {
            put(COLUMN_LAT, location.latitude)
            put(COLUMN_LON, location.longitude)
            put(COLUMN_TIME, location.timestamp)
            put(COLUMN_REGION, location.region)
        }
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun getAll(): List<Location> {
        val list = mutableListOf<Location>()
        readableDatabase.query(TABLE_NAME, null, null, null, null, null, null).use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val lat = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LAT))
                val lon = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LON))
                val time = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIME))
                val region = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REGION))
                list.add(Location(id, lat, lon, time, region))
            }
        }
        return list
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "locations.db"

        const val TABLE_NAME = "locations"
        const val COLUMN_ID = "id"
        const val COLUMN_LAT = "latitude"
        const val COLUMN_LON = "longitude"
        const val COLUMN_TIME = "timestamp"
        const val COLUMN_REGION = "region"
    }
}
