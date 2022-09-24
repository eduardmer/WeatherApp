package com.weatherapp.data.local

import androidx.datastore.core.DataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weatherapp.CityPreferences
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityLocalDataSource @Inject constructor(private val dataStore: DataStore<CityPreferences>, private val inputStream: InputStream) {

    val cityPreferences: Flow<CityPreferences> = dataStore.data

    suspend fun updateCityPreferences(city: City) {
        dataStore.updateData {
            it.toBuilder().setCity(city.city).setLat(city.lat).setLng(city.lng).build()
        }
    }

    suspend fun getCities(): List<City> {
        val cities = inputStream.bufferedReader().use { it.readText() }
        val myType = object : TypeToken<List<City>>() {}.type
        return Gson().fromJson<List<City>>(cities, myType)
    }

}