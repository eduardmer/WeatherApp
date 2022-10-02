package com.weatherapp.data

import com.weatherapp.CityPreferences
import com.weatherapp.data.local.model.CityDto
import com.weatherapp.data.local.LocalDataSource
import com.weatherapp.data.remote.WeatherApiClient
import com.weatherapp.utils.APP_ID
import com.weatherapp.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val client: WeatherApiClient,
                                     private val cityLocalDataSource: LocalDataSource,
                                     private val defaultDispatcher: CoroutineDispatcher) {

    val weather: Flow<Result> = cityLocalDataSource.cityPreferences
        .map<CityPreferences,Result> {
            val currentWeatherResponse = client.getCurrentWeather(it.lat, it.lng, APP_ID)
            val todayWeatherResponse = client.getTodayWeather(it.lat, it.lng, APP_ID)
            Result.Success(currentWeatherResponse, todayWeatherResponse)
        }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it.message)) }

    suspend fun getAllCities(): List<CityDto> =
        withContext(defaultDispatcher) {
            cityLocalDataSource.getCities()
        }

    suspend fun updateSelectedCity(city: CityDto) = cityLocalDataSource.updateCityPreferences(city)

}