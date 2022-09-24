package com.weatherapp.data

import com.weatherapp.data.local.City
import com.weatherapp.data.local.CityLocalDataSource
import com.weatherapp.data.remote.WeatherService
import com.weatherapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(val service: WeatherService, val cityLocalDataSource: CityLocalDataSource, val location: LocationApi) {

    suspend fun getWeather(lat : Double = 41.3275, lon : Double = 19.8187, appid : String) = service.getWeather(lat, lon, appid)

    suspend fun getCurrentWeather(lat : Double = 41.3275, lon : Double = 19.8187, appid : String) = service.getCurrentWeather(lat, lon, appid)

    suspend fun getAllCities(): List<City> =
        withContext(Dispatchers.Default) {
            cityLocalDataSource.getCities()
        }

    suspend fun getCurrentLocation() = location.getLocation()

    val weather: Flow<Resource> = cityLocalDataSource.cityPreferences.map {city ->
        try {
            val currentWeatherResponse = service.getCurrentWeather(city.lat, city.lng, "37f6ca3eb5a94ec5ff7d9600bef088c8")
            val todayWeatherResponse = service.getWeather(city.lat, city.lng, "37f6ca3eb5a94ec5ff7d9600bef088c8")
            Resource.Success(currentWeatherResponse, todayWeatherResponse)
        } catch (ex: Exception) {
            Resource.Error(ex.toString())
        }
    }

    suspend fun updateSelectedCity(city: City) = cityLocalDataSource.updateCityPreferences(city)

}