package com.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.room.Room
import com.weatherapp.CityPreferences
import com.weatherapp.data.CityPreferencesSerializer
import com.weatherapp.data.CityPreferencesSerializer.cityProtoDataStore
import com.weatherapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

}