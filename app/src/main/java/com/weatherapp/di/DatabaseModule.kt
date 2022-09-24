package com.weatherapp.di

import android.content.Context
import android.content.res.AssetManager
import androidx.datastore.core.DataStore
import androidx.room.Room
import com.weatherapp.CityPreferences
import com.weatherapp.data.local.CityPreferencesSerializer.cityProtoDataStore
import com.weatherapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.InputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context : Context) : AppDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "weather_database").build()

    @Provides
    @Singleton
    fun provideProtoDataStore(@ApplicationContext context: Context): DataStore<CityPreferences> {
        return context.cityProtoDataStore
    }

    @Provides
    @Singleton
    fun provideInputStream(@ApplicationContext context: Context): InputStream {
        return context.assets.open("albania_cities.json")
    }

}