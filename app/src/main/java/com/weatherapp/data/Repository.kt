package com.weatherapp.data

import com.weatherapp.data.local.City
import com.weatherapp.data.local.LocalDataSource
import com.weatherapp.data.remote.WeatherService
import com.weatherapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(val service: WeatherService, val cityLocalDataSource: LocalDataSource) {

    val weather: Flow<Result> = cityLocalDataSource.cityPreferences
        .map {
            val currentWeatherResponse = service.getCurrentWeather(it.lat, it.lng, "37f6ca3eb5a94ec5ff7d9600bef0888")
            val todayWeatherResponse = service.getTodayWeather(it.lat, it.lng, "37f6ca3eb5a94ec5ff7d9600bef088c8")
            Result.Success(currentWeatherResponse, todayWeatherResponse)
        }
        .onStart { Result.Loading }
        .catch { Result.Error(it.message) }

    suspend fun getAllCities(): List<City> =
        withContext(Dispatchers.Default) {
            cityLocalDataSource.getCities()
        }

    suspend fun updateSelectedCity(city: City) = cityLocalDataSource.updateCityPreferences(city)

}