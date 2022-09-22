package com.weatherapp.data.local

import androidx.datastore.core.DataStore
import com.weatherapp.CityPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityLocalDataSource @Inject constructor(private val dataStore: DataStore<CityPreferences>) {

    val cityPreferences: Flow<CityPreferences> = dataStore.data

    suspend fun updateCityPreferences(city: City) {
        dataStore.updateData {
            it.toBuilder().setCity(city.city).setLat(city.lat).setLng(city.lng).build()
        }
    }

}