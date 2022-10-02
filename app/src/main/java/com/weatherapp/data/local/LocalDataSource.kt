package com.weatherapp.data.local

import androidx.datastore.core.DataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weatherapp.CityPreferences
import com.weatherapp.data.local.model.CityDto
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val dataStore: DataStore<CityPreferences>, private val inputStream: InputStream) {

    val cityPreferences: Flow<CityPreferences> = dataStore.data

    suspend fun updateCityPreferences(city: CityDto) {
        dataStore.updateData {
            it.toBuilder().setCity(city.city).setLat(city.lat).setLng(city.lng).build()
        }
    }

    fun getCities(): List<CityDto> {
        val cities = inputStream.bufferedReader().use { it.readText() }
        val myType = object : TypeToken<List<CityDto>>() {}.type
        return Gson().fromJson(cities, myType)
    }

}