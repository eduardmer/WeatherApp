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
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(val service: WeatherService, val cityLocalDataSource: LocalDataSource) {

    suspend fun getAllCities(): List<City> =
        withContext(Dispatchers.Default) {
            cityLocalDataSource.getCities()
        }

    val weather: Flow<Result> = cityLocalDataSource.cityPreferences.map { city ->
        try {
            val currentWeatherResponse = service.getCurrentWeather(city.lat, city.lng, "37f6ca3eb5a94ec5ff7d9600bef088c8")
            val todayWeatherResponse = service.getTodayWeather(city.lat, city.lng, "37f6ca3eb5a94ec5ff7d9600bef088c8")
            Result.Success(currentWeatherResponse, todayWeatherResponse)
        } catch (ex: Exception) {
            Result.Error(ex.toString())
        }
    }.onStart { emit(Result.Loading) }
        .catch {emit(Result.Error(it.message))}

    suspend fun updateSelectedCity(city: City) = cityLocalDataSource.updateCityPreferences(city)

}