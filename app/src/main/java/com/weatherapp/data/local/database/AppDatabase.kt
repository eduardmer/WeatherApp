package com.weatherapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weatherapp.data.local.entities.Weather

@Database(entities = [Weather::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

}