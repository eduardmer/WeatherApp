package com.weatherapp.data

import com.weatherapp.data.remote.WeatherService
import javax.inject.Inject

class Repository @Inject constructor(val service: WeatherService, val location: LocationApi) {

    suspend fun getWeather(lat : Double = 41.3275, lon : Double = 19.8187, appid : String) = service.getWeather(lat, lon, appid)

    suspend fun getCurrentWeather(lat : Double = 41.3275, lon : Double = 19.8187, appid : String) = service.getCurrentWeather(lat, lon, appid)

    suspend fun getCurrentLocation() = location.getLocation()

}